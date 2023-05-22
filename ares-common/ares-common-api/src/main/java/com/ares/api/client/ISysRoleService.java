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

package com.ares.api.client;

import com.ares.core.model.system.SysRole;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.ISysRoleService.java
 **/
public interface ISysRoleService {
    /**
     * 根据userId获取角色
     *
     * @param userId
     * @return
     */
    List<SysRole> getRoleByUserId(String userId);

    /**
     * 根据id获取角色权限
     *
     * @param roleId
     * @return
     */
    List<String> getPermsByRoleId(String roleId);

    /**
     * 根据id获取角色
     *
     * @param roleId
     * @return
     */
    SysRole getById(String roleId);

    /**
     * 获取所有角色
     *
     * @return
     */
    List<SysRole> getAll();

    /**
     * 获取角色
     *
     * @param role
     * @return
     */
    List<SysRole> selectRoleList(SysRole role);
}
