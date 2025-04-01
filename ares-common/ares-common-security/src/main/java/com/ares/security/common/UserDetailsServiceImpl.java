/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

package com.ares.security.common;

import com.ares.api.client.ISysRoleService;
import com.ares.api.client.ISysUserService;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.security.jwt.JwtUserDetails;
import org.apache.dubbo.config.annotation.DubboReference;
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

    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysUserService.class, check = false)
    private ISysUserService userService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysRoleService.class, check = false)
    private ISysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = userService.getUserByName(userName);
        if (null == user) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        List<SysRole> roleList = roleService.getRoleByUserId(user.getId());

        List<String> perms = new ArrayList<>();
        for (SysRole role : roleList) {
            if ("gly".equalsIgnoreCase(role.getRoleName())) {
                perms = roleService.getPermsByRoleId(null);
            } else {
                perms = roleService.getPermsByRoleId(role.getId());
            }
        }
        List<GrantedAuthority> grantedAuthorities = perms.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        return new JwtUserDetails(userName, user.getPassword(), grantedAuthorities);
    }
}
