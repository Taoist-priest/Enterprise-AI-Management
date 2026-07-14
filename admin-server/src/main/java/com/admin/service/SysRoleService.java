package com.admin.service;

import com.admin.common.PageResult;
import com.admin.dto.RoleDTO;
import com.admin.entity.SysRole;

import java.util.List;

public interface SysRoleService {

    PageResult<List<SysRole>> page(int current, int size, String roleName, Integer status);

    SysRole getById(Long id);

    List<Long> getMenuIdsByRoleId(Long roleId);

    void create(RoleDTO dto);

    void update(RoleDTO dto);

    void delete(Long id);
}
