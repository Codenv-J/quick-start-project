package com.project.boot.user.controller;


import com.project.boot.model.dto.web.UserLoginDto;
import com.project.boot.model.dto.web.UserRegisterDto;
import com.project.boot.model.vo.UserInfoVo;
import com.project.boot.model.vo.common.Result;
import com.project.boot.model.vo.common.ResultCodeEnum;
import com.project.boot.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "平台用户接口")
@RestController
@RequestMapping("api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "平台用户注册")
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "平台用户登录")
    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        return Result.build(userService.login(userLoginDto), ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userService.getCurrentUserInfo(token) ;
        return Result.build(userInfoVo , ResultCodeEnum.SUCCESS) ;
    }

}
