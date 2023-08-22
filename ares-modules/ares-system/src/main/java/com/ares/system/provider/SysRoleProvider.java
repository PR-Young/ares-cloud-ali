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

package com.ares.system.provider;

import com.ares.api.client.ISysRoleService;
import com.ares.core.model.query.SysRoleQuery;
import com.ares.core.model.system.SysRole;
import com.ares.system.service.SysRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2023/5/19
 * @see: com.ares.system.provider.SysRoleProvider.java
 **/
@DubboService(version = "1.0.0",interfaceClass = com.ares.api.client.ISysRoleService.class)
public class SysRoleProvider implements ISysRoleService {

    @Autowired
    SysRoleService roleService;

    @Override
    public List<SysRole> getRoleByUserId(Long userId) {
        return roleService.getRoleByUserId(userId);
    }

    @Override
    public List<String> getPermsByRoleId(Long roleId) {
        return roleService.getPermsByRoleId(roleId);
    }

    @Override
    public SysRole getById(Long roleId) {
        return roleService.getById(roleId);
    }

    @Override
    public List<SysRole> getAll() {
        return roleService.getAll();
    }

    @Override
    public List<SysRole> selectRoleList(SysRoleQuery role) {
        return roleService.selectRoleList(role);
    }
}
