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
import com.ares.core.model.query.SysUserQuery;
import com.ares.core.model.system.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/22
 **/
@Repository
public interface ISysUserDao extends IBaseDao<SysUser, SysUserQuery> {

    SysUser getUserByName(String userName);

    Integer checkAccount(String account);

    int resetPassword(@Param("password") String password, @Param("id") Long id);

    List<SysUser> getUserByRole(Long roleId);

    List<SysUser> allUser(Long roleId);

    int updateUserByAccount(SysUser sysUser);

}
