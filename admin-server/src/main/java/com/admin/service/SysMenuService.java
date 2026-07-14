package com.admin.service;

import com.admin.dto.MenuDTO;
import com.admin.dto.MenuVO;
import com.admin.entity.SysMenu;

import java.util.List;

public interface SysMenuService {

    List<MenuVO> tree();

    SysMenu getById(Long id);

    void create(MenuDTO dto);

    void update(MenuDTO dto);

    void delete(Long id);
}
