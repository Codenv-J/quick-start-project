package com.project.boot.manager.mapper;

import com.project.boot.model.dto.system.SysUserDto;
import com.project.boot.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    public SysUser selectByUserName(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    SysUser findByUserName(String userName);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);
}
