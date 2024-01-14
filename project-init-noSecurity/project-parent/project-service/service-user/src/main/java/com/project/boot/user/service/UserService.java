package com.project.boot.user.service;

import com.project.boot.model.dto.web.UserLoginDto;
import com.project.boot.model.dto.web.UserRegisterDto;
import com.project.boot.model.vo.UserInfoVo;

public interface UserService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
