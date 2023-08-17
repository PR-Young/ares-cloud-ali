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

package com.ares.flowable.persistence.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.flowable.model.query.SysDeployFormQuery;
import com.ares.flowable.persistence.model.SysDeployForm;
import com.ares.flowable.persistence.model.SysForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysDeployFormDao extends IBaseDao<SysDeployForm, SysDeployFormQuery> {
    /**
     * 查询流程实例关联表单
     *
     * @param id 流程实例关联表单ID
     * @return 流程实例关联表单
     */
    public SysDeployForm selectSysDeployFormById(Long id);

    /**
     * 查询流程实例关联表单列表
     *
     * @param SysDeployForm 流程实例关联表单
     * @return 流程实例关联表单集合
     */
    public List<SysDeployForm> selectSysDeployFormList(SysDeployFormQuery SysDeployForm);


    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     * @return
     */
    SysForm selectSysDeployFormByDeployId(String deployId);
}
