package com.admin.service.impl;

import com.admin.dto.MenuDTO;
import com.admin.dto.MenuVO;
import com.admin.entity.SysMenu;
import com.admin.exception.BusinessException;
import com.admin.mapper.SysMenuMapper;
import com.admin.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper menuMapper;

    @Override
    public List<MenuVO> tree() {
        List<SysMenu> menus = menuMapper.selectList(null);
        return buildTree(menus);
    }

    @Override
    public SysMenu getById(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void create(MenuDTO dto) {
        menuMapper.insert(toEntity(dto));
    }

    @Override
    public void update(MenuDTO dto) {
        if (menuMapper.selectById(dto.getId()) == null) {
            throw new BusinessException("菜单不存在");
        }
        menuMapper.updateById(toEntity(dto));
    }

    @Override
    public void delete(Long id) {
        menuMapper.deleteById(id);
    }

    private List<MenuVO> buildTree(List<SysMenu> menus) {
        List<MenuVO> voList = menus.stream().map(this::toVO).collect(Collectors.toList());
        Map<Long, List<MenuVO>> childrenMap = voList.stream()
                .filter(m -> m.getParentId() != 0)
                .collect(Collectors.groupingBy(MenuVO::getParentId));
        voList.forEach(m -> m.setChildren(childrenMap.getOrDefault(m.getId(), new ArrayList<>())));
        return voList.stream().filter(m -> m.getParentId() == 0).collect(Collectors.toList());
    }

    private MenuVO toVO(SysMenu menu) {
        MenuVO vo = new MenuVO();
        vo.setId(menu.getId());
        vo.setParentId(menu.getParentId());
        vo.setMenuName(menu.getMenuName());
        vo.setMenuType(menu.getMenuType());
        vo.setPath(menu.getPath());
        vo.setComponent(menu.getComponent());
        vo.setPermission(menu.getPermission());
        vo.setIcon(menu.getIcon());
        vo.setSortOrder(menu.getSortOrder());
        return vo;
    }

    private SysMenu toEntity(MenuDTO dto) {
        SysMenu menu = new SysMenu();
        menu.setId(dto.getId());
        menu.setParentId(dto.getParentId());
        menu.setMenuName(dto.getMenuName());
        menu.setMenuType(dto.getMenuType());
        menu.setPath(dto.getPath());
        menu.setComponent(dto.getComponent());
        menu.setPermission(dto.getPermission());
        menu.setIcon(dto.getIcon());
        menu.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        menu.setStatus(dto.getStatus());
        return menu;
    }
}
