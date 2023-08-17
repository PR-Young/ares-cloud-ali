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

import com.ares.api.client.ISysUserService;
import com.ares.core.model.query.SysUserQuery;
import com.ares.core.model.system.SysUser;
import com.ares.system.service.SysUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2023/5/19
 * @see: com.ares.system.provider.SysUserProvider.java
 **/
@DubboService
public class SysUserProvider implements ISysUserService {
    @Resource
    SysUserService userService;

    @Override
    public SysUser getUserByName(String name) {
        return userService.getUserByName(name);
    }

    @Override
    public SysUser getById(Long id) {
        return userService.getById(id);
    }

    @Override
    public List<SysUser> selectUserList(SysUserQuery sysUser) {
        return userService.selectUserList(sysUser);
    }
}
