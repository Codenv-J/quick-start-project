package com.project.boot.manager.controller;

import com.project.boot.manager.service.SysMenuService;
import com.project.boot.manager.service.SysUserService;
import com.project.boot.manager.service.ValidateCodeService;
import com.project.boot.model.dto.system.LoginDto;
import com.project.boot.model.entity.system.SysUser;
import com.project.boot.model.vo.common.Result;
import com.project.boot.model.vo.common.ResultCodeEnum;
import com.project.boot.model.vo.system.LoginVo;
import com.project.boot.model.vo.system.SysMenuVo;
import com.project.boot.model.vo.system.ValidateCodeVo;
import com.project.boot.common.utils.UserHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private SysMenuService sysMenuService;


    @Autowired
    private ValidateCodeService validateCodeService;
    
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }


    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        SysUser sysUser = UserHolder.get();
        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
    }


    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findUserMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }


}