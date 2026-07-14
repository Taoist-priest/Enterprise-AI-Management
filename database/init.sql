-- ============================================================
-- 企业级后台管理系统 - 数据库初始化脚本
-- MySQL 8.0+
-- ============================================================

CREATE DATABASE IF NOT EXISTS `admin_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `admin_system`;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username`    VARCHAR(50)  NOT NULL COMMENT '用户名',
  `password`    VARCHAR(100) NOT NULL COMMENT '密码（BCrypt加密）',
  `nickname`    VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
  `email`       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone`       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
  `avatar`      VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name`   VARCHAR(50)  NOT NULL COMMENT '角色名称',
  `role_code`   VARCHAR(50)  NOT NULL COMMENT '角色编码',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- 菜单表
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id`   BIGINT       NOT NULL DEFAULT 0 COMMENT '父菜单ID，0为顶级',
  `menu_name`   VARCHAR(50)  NOT NULL COMMENT '菜单名称',
  `menu_type`   TINYINT      NOT NULL COMMENT '类型：1-目录 2-菜单 3-按钮',
  `path`        VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
  `component`   VARCHAR(200) DEFAULT NULL COMMENT '组件路径',
  `permission`  VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
  `icon`        VARCHAR(50)  DEFAULT NULL COMMENT '图标',
  `sort_order`  INT          NOT NULL DEFAULT 0 COMMENT '排序',
  `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- 用户角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id`      BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ----------------------------
-- 角色菜单关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id`      BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- ============================================================
-- 初始化测试数据
-- 默认密码均为 admin123（BCrypt加密）
-- ============================================================

-- 用户数据
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `status`) VALUES
(1, 'admin',   '$2a$10$REtZHDEhVZIEQ2sCVwc8P.5TMe0JM0IFioqXVn12Sk2bqvw6zVuky', '超级管理员', 'admin@example.com', '13800000001', 1),
(2, 'editor',  '$2a$10$REtZHDEhVZIEQ2sCVwc8P.5TMe0JM0IFioqXVn12Sk2bqvw6zVuky', '编辑员',     'editor@example.com', '13800000002', 1),
(3, 'viewer',  '$2a$10$REtZHDEhVZIEQ2sCVwc8P.5TMe0JM0IFioqXVn12Sk2bqvw6zVuky', '查看员',     'viewer@example.com', '13800000003', 1);

-- 角色数据
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `status`) VALUES
(1, '超级管理员', 'SUPER_ADMIN', '拥有所有权限', 1),
(2, '编辑员',     'EDITOR',      '拥有编辑权限', 1),
(3, '查看员',     'VIEWER',      '仅拥有查看权限', 1);

-- 菜单数据
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_type`, `path`, `component`, `permission`, `icon`, `sort_order`, `status`) VALUES
-- 一级目录
(1,  0, '系统管理', '1', '/system',     NULL,                    NULL,              'Setting',  1, 1),
(2,  0, '数据看板', '2', '/dashboard',  'dashboard/index',       'dashboard:view',  'DataLine', 0, 1),
-- 系统管理子菜单
(10, 1, '用户管理', '2', '/system/user',  'system/user/index',   'system:user:list',  'User',       1, 1),
(11, 1, '角色管理', '2', '/system/role',  'system/role/index',   'system:role:list',  'UserFilled', 2, 1),
(12, 1, '菜单管理', '2', '/system/menu',  'system/menu/index',   'system:menu:list',  'Menu',       3, 1),
-- 用户管理按钮
(101, 10, '新增用户', '3', NULL, NULL, 'system:user:add',    NULL, 1, 1),
(102, 10, '编辑用户', '3', NULL, NULL, 'system:user:edit',   NULL, 2, 1),
(103, 10, '删除用户', '3', NULL, NULL, 'system:user:delete', NULL, 3, 1),
(104, 10, '导出用户', '3', NULL, NULL, 'system:user:export', NULL, 4, 1),
-- 角色管理按钮
(111, 11, '新增角色', '3', NULL, NULL, 'system:role:add',    NULL, 1, 1),
(112, 11, '编辑角色', '3', NULL, NULL, 'system:role:edit',   NULL, 2, 1),
(113, 11, '删除角色', '3', NULL, NULL, 'system:role:delete', NULL, 3, 1),
-- 菜单管理按钮
(121, 12, '新增菜单', '3', NULL, NULL, 'system:menu:add',    NULL, 1, 1),
(122, 12, '编辑菜单', '3', NULL, NULL, 'system:menu:edit',   NULL, 2, 1),
(123, 12, '删除菜单', '3', NULL, NULL, 'system:menu:delete', NULL, 3, 1);

-- 用户角色关联
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

-- 角色菜单关联（超级管理员拥有所有菜单）
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(1, 1), (1, 2), (1, 10), (1, 11), (1, 12),
(1, 101), (1, 102), (1, 103), (1, 104),
(1, 111), (1, 112), (1, 113),
(1, 121), (1, 122), (1, 123);

-- 编辑员角色菜单（无删除权限）
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(2, 1), (2, 2), (2, 10), (2, 11), (2, 12),
(2, 101), (2, 102), (2, 104),
(2, 111), (2, 112),
(2, 121), (2, 122);

-- 查看员角色菜单（仅查看）
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(3, 1), (3, 2), (3, 10), (3, 11), (3, 12);
