package com.admin.controller;

import com.admin.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 健康检查 / 欢迎页（用于验证服务是否启动）
 */
@RestController
public class HealthController {

    @GetMapping({"/", "/health"})
    public Result<Map<String, String>> health() {
        return Result.success(Map.of(
                "status", "UP",
                "service", "admin-server",
                "message", "服务运行正常，请通过 /auth/login 登录或访问前端 http://localhost:3000"
        ));
    }
}
