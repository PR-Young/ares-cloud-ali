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

package com.ares.system.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/25
 **/
@Repository
public interface ISysRoleDao extends IBaseDao<SysRole> {
    List<SysRole> getRoleByUserId(String userId);

    List<String> getPermsByRoleId(String roleId);

    int insertRoleUser(Map<String, Object> map);

    int insertPermission(Map<String, Object> map);

    int deleteRoleUser(String roleId);

    int deletePermission(String roleId);

    int deleteRoleByUser(String userId);

    int checkRoleName(String roleName);

    SysRole getRoleByName(String roleName);
}
