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
import com.ares.flowable.persistence.dao.ISysDeployFormDao;
import com.ares.flowable.persistence.model.SysDeployForm;
import com.ares.flowable.persistence.model.SysForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysDeployFormService implements BaseService<SysDeployForm> {

    private ISysDeployFormDao sysDeployFormDao;

    @Autowired
    public SysDeployFormService(ISysDeployFormDao sysDeployFormDao) {
        this.sysDeployFormDao = sysDeployFormDao;
    }

    public PageInfo<SysDeployForm> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysDeployForm> lists = sysDeployFormDao.list(map);
        PageInfo<SysDeployForm> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysDeployForm obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysDeployFormDao.insert(obj);
    }

    @Override
    public void update(SysDeployForm obj) {
        obj.setModifyTime(new Date());
        sysDeployFormDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysDeployFormDao.deleteByIds(ids);
    }

    @Override
    public SysDeployForm getById(String id) {
        return sysDeployFormDao.getById(id);
    }

    public List<SysDeployForm> list(SysDeployForm obj) {
        List<SysDeployForm> lists = sysDeployFormDao.selectList(obj);
        return lists;
    }

    public SysDeployForm selectSysDeployFormById(String id) {
        return sysDeployFormDao.selectSysDeployFormById(id);
    }

    public SysForm selectSysDeployFormByDeployId(String id) {
        return sysDeployFormDao.selectSysDeployFormByDeployId(id);
    }

}
