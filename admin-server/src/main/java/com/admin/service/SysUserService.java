package com.admin.service;

import com.admin.common.PageResult;
import com.admin.dto.UserDTO;
import com.admin.entity.SysUser;

public interface SysUserService {

    PageResult<java.util.List<SysUser>> page(int current, int size, String username, Integer status);

    SysUser getById(Long id);

    void create(UserDTO dto);

    void update(UserDTO dto);

    void delete(Long id);

    java.util.List<SysUser> export(String username, Integer status);
}
