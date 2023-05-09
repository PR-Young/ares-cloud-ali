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

    private ISysUserService userService;
    private ISysRoleService roleService;

    @Autowired
    public UserDetailsServiceImpl(ISysUserService userService, ISysRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

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
            //if ("gly".equalsIgnoreCase(role.getRoleName())) {
            //    JsonResult<List<String>> permsResult = roleService.getPermsByRoleId(null);
            //    perms = permsResult.getData();
            //} else {
            JsonResult<List<String>> permsResult = roleService.getPermsByRoleId(role.getId());
            perms = permsResult.getData();
            //}
        }
        List<GrantedAuthority> grantedAuthorities = perms.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        return new JwtUserDetails(userName, user.getPassword(), grantedAuthorities);
    }
}
