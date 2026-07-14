package com.admin.service;

import com.admin.dto.LoginDTO;
import com.admin.dto.LoginVO;

public interface AuthService {

    LoginVO login(LoginDTO loginDTO);

    LoginVO getUserInfo(String username);
}
