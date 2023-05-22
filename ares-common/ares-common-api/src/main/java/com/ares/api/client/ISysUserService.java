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

import com.ares.core.model.system.SysUser;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.ISysUserService.java
 **/
public interface ISysUserService {
    /**
     * 根据用户名获取用户
     *
     * @param name
     * @return
     */
    SysUser getUserByName(String name);

    /**
     * 根据Id获取用户
     *
     * @param id
     * @return
     */
    SysUser getById(String id);

    /**
     * 获取用户
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectUserList(SysUser sysUser);
}
