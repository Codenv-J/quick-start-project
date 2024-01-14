package com.project.boot.manager.mapper;

import com.project.boot.model.dto.system.SysRoleDto;
import com.project.boot.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    public  List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);


    List<SysRole> findAllRoles();

    void deleteById(Long roleId);
}
