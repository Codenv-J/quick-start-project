package com.project.boot.manager.service;


import com.github.pagehelper.PageInfo;
import com.project.boot.model.dto.system.AssginRoleDto;
import com.project.boot.model.dto.system.LoginDto;
import com.project.boot.model.dto.system.SysUserDto;
import com.project.boot.model.entity.system.SysUser;
import com.project.boot.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
