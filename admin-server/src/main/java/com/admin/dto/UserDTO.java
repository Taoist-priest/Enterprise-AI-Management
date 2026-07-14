package com.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 用户创建/更新 DTO
 */
@Data
public class UserDTO {

  private Long id;

  @NotBlank(message = "用户名不能为空")
  private String username;

  private String password;

  private String nickname;
  private String email;
  private String phone;
  private String avatar;

  @NotNull(message = "状态不能为空")
  private Integer status;

  private List<Long> roleIds;
}
