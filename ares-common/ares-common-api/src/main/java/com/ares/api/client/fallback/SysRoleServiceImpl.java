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

package com.ares.api.client.fallback;

import com.ares.api.client.ISysRoleService;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysRole;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.fallback.SysRoleServiceImpl.java
 **/
public class SysRoleServiceImpl implements ISysRoleService {

    @Override
    public JsonResult<List<SysRole>> getRoleByUserId(String userId) {
        return JsonResult.error(null, 1000, "error");
    }

    @Override
    public JsonResult<List<String>> getPermsByRoleId(String roleId) {
        return JsonResult.error(null, 1000, "error");
    }

    @Override
    public JsonResult<SysRole> getById(String roleId) {
        return JsonResult.error(null, 1000, "error");
    }

    @Override
    public JsonResult<List<SysRole>> getAll() {
        return JsonResult.error(null, 1000, "error");
    }

    @Override
    public JsonResult<List<SysRole>> selectRoleList(SysRole role) {
        return JsonResult.error(null, 1000, "error");
    }
}
