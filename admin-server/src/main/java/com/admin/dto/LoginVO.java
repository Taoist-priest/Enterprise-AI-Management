package com.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * 登录响应 VO
 */
@Data
public class LoginVO {

    private String token;
    private UserInfoVO userInfo;
    private List<String> permissions;
    private List<MenuVO> menus;
}
