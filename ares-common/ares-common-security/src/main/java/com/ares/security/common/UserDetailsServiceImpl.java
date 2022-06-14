package com.ares.security.common;

import com.ares.api.client.ISysRoleService;
import com.ares.api.client.ISysUserService;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.log.common.Log;
import com.ares.security.jwt.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Young
 * @date: 2020/10/19
 * @see: com.ares.system.persistence.service UserDetailsService.java
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;


    @Log
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        JsonResult<SysUser> userResult = userService.getUserByName(userName);
        SysUser user = userResult.getData();
        if (null == user) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        JsonResult<List<SysRole>> roleResult = roleService.getRoleByUserId(user.getId());
        List<SysRole> roleList = roleResult.getData();

        List<String> perms = new ArrayList<>();
        for (SysRole role : roleList) {
            if ("gly".equalsIgnoreCase(role.getRoleName())) {
                JsonResult<List<String>> permsResult = roleService.getPermsByRoleId(null);
                perms = permsResult.getData();
            } else {
                JsonResult<List<String>> permsResult = roleService.getPermsByRoleId(role.getId());
                perms = permsResult.getData();
            }
        }
        List<GrantedAuthority> grantedAuthorities = perms.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        return new JwtUserDetails(userName, user.getPassword(), grantedAuthorities);
    }
}
