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
import com.ares.core.model.query.SysMenuQuery;
import com.ares.core.model.system.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/23
 **/
@Repository
public interface ISysMenuDao extends IBaseDao<SysMenu, SysMenuQuery> {

    List<SysMenu> getMenuByUserId(Long userId);

    SysMenu getByPId(Long pid);

    List<String> getMenuByRole(Long roleId);

    List<SysMenu> selectListByUser(SysMenuQuery menu);

    int deleteById(Long menuId);

    int hasChildByMenuId(Long menuId);

}
