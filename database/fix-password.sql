-- 修复测试账号密码为 admin123（若登录报用户名或密码错误可执行此脚本）
USE `admin_system`;

UPDATE `sys_user`
SET `password` = '$2a$10$REtZHDEhVZIEQ2sCVwc8P.5TMe0JM0IFioqXVn12Sk2bqvw6zVuky'
WHERE `username` IN ('admin', 'editor', 'viewer');
