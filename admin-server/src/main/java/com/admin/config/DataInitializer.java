package com.admin.config;

import com.admin.entity.SysUser;
import com.admin.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 启动时校验并修复测试账号密码，避免 init.sql 中 BCrypt 哈希与 admin123 不一致导致无法登录
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private static final String DEFAULT_PASSWORD = "admin123";
    private static final String[] TEST_USERS = {"admin", "editor", "viewer"};

    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        for (String username : TEST_USERS) {
            SysUser user = userMapper.selectByUsername(username);
            if (user != null && !passwordEncoder.matches(DEFAULT_PASSWORD, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
                userMapper.updateById(user);
                log.info("已重置测试账号 [{}] 的密码为: {}", username, DEFAULT_PASSWORD);
            }
        }
    }
}
