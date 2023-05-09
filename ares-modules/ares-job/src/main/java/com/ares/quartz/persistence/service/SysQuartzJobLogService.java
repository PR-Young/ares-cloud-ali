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

package com.ares.quartz.persistence.service;

import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.quartz.persistence.dao.ISysQuartzJobLogDao;
import com.ares.quartz.persistence.model.SysQuartzJobLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/29
 **/
@Service
public class SysQuartzJobLogService implements BaseService<SysQuartzJobLog> {

    private ISysQuartzJobLogDao sysQuartzJobLogDao;

    @Autowired
    public SysQuartzJobLogService(ISysQuartzJobLogDao sysQuartzJobLogDao) {
        this.sysQuartzJobLogDao = sysQuartzJobLogDao;
    }

    @Override
    public void insert(SysQuartzJobLog obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        sysQuartzJobLogDao.insert(obj);
    }

    @Override
    public void update(SysQuartzJobLog obj) {

    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysQuartzJobLogDao.deleteByIds(ids);
    }

    @Override
    public SysQuartzJobLog getById(String id) {
        return sysQuartzJobLogDao.getById(id);
    }

    public PageInfo<SysQuartzJobLog> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysQuartzJobLog> sysQuartzJobLogList = sysQuartzJobLogDao.list(map);
        PageInfo<SysQuartzJobLog> jobPageInfo = new PageInfo<>(sysQuartzJobLogList);
        return jobPageInfo;
    }

    public List<SysQuartzJobLog> selectJobLogList(SysQuartzJobLog jobLog) {
        return sysQuartzJobLogDao.selectList(jobLog);
    }

    public void cleanJobLog() {
        sysQuartzJobLogDao.cleanJobLog();
    }
}
