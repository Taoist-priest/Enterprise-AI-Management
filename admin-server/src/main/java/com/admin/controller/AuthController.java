package com.admin.controller;

import com.admin.common.Result;
import com.admin.dto.LoginDTO;
import com.admin.dto.LoginVO;
import com.admin.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @GetMapping("/info")
    public Result<LoginVO> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return Result.success(authService.getUserInfo(userDetails.getUsername()));
    }
}
