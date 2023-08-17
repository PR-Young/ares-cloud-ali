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

package com.ares.flowable.persistence.service;

import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.flowable.model.query.SysTaskFormQuery;
import com.ares.flowable.persistence.dao.ISysTaskFormDao;
import com.ares.flowable.persistence.model.SysTaskForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysTaskFormService implements BaseService<SysTaskForm> {

    private ISysTaskFormDao sysTaskFormDao;

    @Autowired
    public SysTaskFormService(ISysTaskFormDao sysTaskFormDao) {
        this.sysTaskFormDao = sysTaskFormDao;
    }

    /**
     * 查询流程任务关联单
     *
     * @param id 流程任务关联单ID
     * @return 流程任务关联单
     */
    public SysTaskForm selectSysTaskFormById(Long id) {
        return sysTaskFormDao.getById(id);
    }

    /**
     * 查询流程任务关联单列表
     *
     * @param sysTaskForm 流程任务关联单
     * @return 流程任务关联单
     */

    public List<SysTaskForm> selectSysTaskFormList(SysTaskForm sysTaskForm) {
        return sysTaskFormDao.selectList(sysTaskForm);
    }

    public PageInfo<SysTaskForm> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysTaskForm> lists = sysTaskFormDao.list(map);
        PageInfo<SysTaskForm> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysTaskForm obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysTaskFormDao.insert(obj);
    }

    @Override
    public void update(SysTaskForm obj) {
        obj.setModifyTime(new Date());
        sysTaskFormDao.update(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        sysTaskFormDao.deleteByIds(ids);
    }

    @Override
    public SysTaskForm getById(Long id) {
        return sysTaskFormDao.getById(id);
    }

    public List<SysTaskForm> list(SysTaskFormQuery obj) {
        List<SysTaskForm> lists = sysTaskFormDao.selectList(obj);
        return lists;
    }

}
