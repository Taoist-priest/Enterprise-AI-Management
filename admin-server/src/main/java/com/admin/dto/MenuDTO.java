package com.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 菜单创建/更新 DTO
 */
@Data
public class MenuDTO {

    private Long id;

    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    private String path;
    private String component;
    private String permission;
    private String icon;
    private Integer sortOrder;

    @NotNull(message = "状态不能为空")
    private Integer status;
}
