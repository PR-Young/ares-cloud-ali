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

import com.ares.api.client.fallback.SysUserServiceImpl;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.ISysUserService.java
 **/
@FeignClient(name = "ares-system", fallback = SysUserServiceImpl.class)
public interface ISysUserService {
    /**
     * 根据用户名获取用户
     *
     * @param name
     * @return
     */
    @GetMapping("/system/system/user/getUserByName/{name}")
    JsonResult<SysUser> getUserByName(@PathVariable("name") String name);

    /**
     * 根据Id获取用户
     *
     * @param id
     * @return
     */
    @GetMapping("/system/system/user/getUserById/{id}")
    JsonResult<SysUser> getById(@PathVariable("id") String id);

    /**
     * 获取用户
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/system/system/user/userList")
    JsonResult<List<SysUser>> selectUserList(SysUser sysUser);
}
