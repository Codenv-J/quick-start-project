package com.project.boot.manager.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.boot.common.exception.ProjectException;
import com.project.boot.manager.mapper.SysRoleUserMapper;
import com.project.boot.manager.mapper.SysUserMapper;
import com.project.boot.manager.service.SysUserService;
import com.project.boot.model.dto.system.AssginRoleDto;
import com.project.boot.model.dto.system.LoginDto;
import com.project.boot.model.dto.system.SysUserDto;
import com.project.boot.model.entity.system.SysUser;
import com.project.boot.model.vo.common.ResultCodeEnum;
import com.project.boot.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper ;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public LoginVo login(LoginDto loginDto) {

        // 校验验证码是否正确
        String captcha = loginDto.getCaptcha();     // 用户输入的验证码
        String codeKey = loginDto.getCodeKey();     // redis中验证码的数据key

        // 从Redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode , captcha)) {
            throw new ProjectException(ResultCodeEnum.VALIDATECODE_ERROR) ;
        }

        // 验证通过删除redis中的验证码
        redisTemplate.delete("user:login:validatecode:" + codeKey) ;

        // 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if(sysUser == null) {
            throw new ProjectException(ResultCodeEnum.LOGIN_ERROR) ;
        }

        // 验证密码是否正确
        String inputPassword = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if(!md5InputPassword.equals(sysUser.getPassword())) {
            throw new ProjectException(ResultCodeEnum.LOGIN_ERROR) ;
        }

        // 生成令牌，保存数据到Redis中
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 30 , TimeUnit.MINUTES);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        // 返回
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson , SysUser.class) ;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token) ;
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum , pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto) ;
        PageInfo pageInfo = new PageInfo(sysUserList) ;
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        // 根据输入的用户名查询用户
        SysUser dbSysUser = sysUserMapper.findByUserName(sysUser.getUserName()) ;
        if(dbSysUser != null) {
            throw new ProjectException(ResultCodeEnum.USER_NAME_IS_EXISTS) ;
        }

        // 对密码进行加密
        String password = sysUser.getPassword();
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        sysUser.setStatus(0);
        sysUserMapper.saveSysUser(sysUser) ;
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser) ;
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId) ;
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        // 删除之前的所有的用户所对应的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId()) ;
        // 分配新的角色数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        roleIdList.forEach(roleId->{
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        });
    }
}
