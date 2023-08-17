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
import com.ares.quartz.common.ScheduleConstants;
import com.ares.quartz.common.ScheduleManager;
import com.ares.quartz.model.query.SysQuartzJobQuery;
import com.ares.quartz.persistence.dao.ISysQuartzJobDao;
import com.ares.quartz.persistence.model.SysQuartzJob;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/29
 **/
@Service
public class SysQuartzJobService implements BaseService<SysQuartzJob> {

    private ISysQuartzJobDao sysQuartzJobDao;
    private ScheduleManager scheduler;

    @Autowired
    public SysQuartzJobService(ISysQuartzJobDao sysQuartzJobDao, ScheduleManager scheduler) {
        this.sysQuartzJobDao = sysQuartzJobDao;
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        List<SysQuartzJob> jobList = list();
        if (null != jobList && jobList.size() > 0) {
            for (SysQuartzJob job : jobList) {
                try {
                    if (ScheduleConstants.Status.NORMAL.getValue().equals(job.getStatus())) {
                        if (scheduler.checkJobExist(job)) {
                            scheduler.delete(scheduler.createTaskName(job.getJobName()), job.getJobGroup());
                        }
                        scheduler.addJob(job);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public PageInfo<SysQuartzJob> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysQuartzJob> sysQuartzJobList = sysQuartzJobDao.list(map);
        PageInfo<SysQuartzJob> jobPageInfo = new PageInfo<>(sysQuartzJobList);
        return jobPageInfo;
    }

    public List<SysQuartzJob> list() {
        List<SysQuartzJob> sysQuartzJobList = sysQuartzJobDao.list(new HashMap<>());
        return sysQuartzJobList;
    }

    @Override
    public void insert(SysQuartzJob obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysQuartzJobDao.insert(obj);
        scheduler.addJob(obj);
    }

    @Override
    public void update(SysQuartzJob obj) {
        obj.setModifyTime(new Date());
        sysQuartzJobDao.update(obj);
        scheduler.edit(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            SysQuartzJob job = getById(id);
            scheduler.delete(scheduler.createTaskName(job.getJobName()), job.getJobGroup());
        }
        sysQuartzJobDao.deleteByIds(ids);
    }

    @Override
    public SysQuartzJob getById(Long id) {
        return sysQuartzJobDao.getById(id);
    }

    public int checkUnique(String jobName) {
        Integer num = sysQuartzJobDao.checkUnique(jobName);
        if (null != num && num > 0) {
            return num;
        }
        return 0;
    }

    @Transactional
    public int resumeJob(SysQuartzJob job) {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = sysQuartzJobDao.update(job);
        if (rows > 0) {
            scheduler.start(scheduler.createTaskName(job.getJobName()), job.getJobGroup());
        }
        return rows;
    }

    @Transactional
    public int pauseJob(SysQuartzJob job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = sysQuartzJobDao.update(job);
        if (rows > 0) {
            scheduler.pause(scheduler.createTaskName(job.getJobName()), job.getJobGroup());
        }
        return rows;
    }

    @Transactional
    public int changeStatus(SysQuartzJob job) {
        int rows = 0;
        Integer status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }


    @Transactional
    public void run(SysQuartzJob job) throws SchedulerException {
        scheduler.run(job);
    }

    public List<SysQuartzJob> selectJobList(SysQuartzJobQuery job) {
        List<SysQuartzJob> sysQuartzJobList = sysQuartzJobDao.selectList(job);
        return sysQuartzJobList;
    }

}
