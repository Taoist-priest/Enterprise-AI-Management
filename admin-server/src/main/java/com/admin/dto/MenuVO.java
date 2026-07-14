package com.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单树 VO（用于前端动态路由）
 */
@Data
public class MenuVO {

    private Long id;
    private Long parentId;
    private String menuName;
    private Integer menuType;
    private String path;
    private String component;
    private String permission;
    private String icon;
    private Integer sortOrder;
    private List<MenuVO> children;
}
