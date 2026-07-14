package com.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户信息 VO
 */
@Data
public class UserInfoVO {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private List<String> roles;
}
