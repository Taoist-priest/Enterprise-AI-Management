package com.admin.service.impl;

import com.admin.dto.LoginDTO;
import com.admin.dto.LoginVO;
import com.admin.dto.MenuVO;
import com.admin.dto.UserInfoVO;
import com.admin.entity.SysMenu;
import com.admin.entity.SysUser;
import com.admin.exception.BusinessException;
import com.admin.mapper.SysMenuMapper;
import com.admin.mapper.SysUserMapper;
import com.admin.service.AuthService;
import com.admin.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SysUserMapper userMapper;
    private final SysMenuMapper menuMapper;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        return buildLoginVO(loginDTO.getUsername());
    }

    @Override
    public LoginVO getUserInfo(String username) {
        return buildLoginVO(username);
    }

    private LoginVO buildLoginVO(String username) {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        String token = jwtUtils.generateToken(username);
        List<String> permissions = userMapper.selectPermissionsByUserId(user.getId());
        List<String> roleCodes = userMapper.selectRoleCodesByUserId(user.getId());
        List<SysMenu> menus = menuMapper.selectMenusByUserId(user.getId());
        List<MenuVO> menuTree = buildMenuTree(menus);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setRoles(roleCodes);

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUserInfo(userInfo);
        vo.setPermissions(permissions);
        vo.setMenus(menuTree);
        return vo;
    }

    /** 构建菜单树（仅目录和菜单，不含按钮） */
    private List<MenuVO> buildMenuTree(List<SysMenu> menus) {
        List<MenuVO> voList = menus.stream()
                .filter(m -> m.getMenuType() != 3)
                .map(this::toMenuVO)
                .collect(Collectors.toList());

        Map<Long, List<MenuVO>> childrenMap = voList.stream()
                .filter(m -> m.getParentId() != 0)
                .collect(Collectors.groupingBy(MenuVO::getParentId));

        voList.forEach(m -> m.setChildren(childrenMap.getOrDefault(m.getId(), new ArrayList<>())));

        return voList.stream()
                .filter(m -> m.getParentId() == 0)
                .collect(Collectors.toList());
    }

    private MenuVO toMenuVO(SysMenu menu) {
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
}
