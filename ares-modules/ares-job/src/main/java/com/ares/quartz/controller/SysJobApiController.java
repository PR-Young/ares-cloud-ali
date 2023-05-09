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

package com.ares.quartz.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.utils.StringUtils;
import com.ares.quartz.persistence.model.SysQuartzJob;
import com.ares.quartz.persistence.service.SysQuartzJobService;
import com.ares.security.common.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 定时任务
 * @author: Young 2020/05/06
 **/
@RestController
@RequestMapping("/monitor/job/*")
@Api(value = "系统任务API", tags = {"系统任务"})
public class SysJobApiController extends BaseController {

    private SysQuartzJobService jobService;

    @Autowired
    public SysJobApiController(SysQuartzJobService jobService) {
        this.jobService = jobService;
    }

    @PreAuthorize("hasAnyAuthority('quartz:list')")
    @RequestMapping("list")
    @ApiOperation(value = "任务列表", response = TableDataInfo.class)
    public TableDataInfo list(SysQuartzJob job) {
        startPage();
        List<SysQuartzJob> jobList = jobService.selectJobList(job);
        return getDataTable(jobList);
    }

    @GetMapping("{jobId}")
    @ApiOperation(value = "根据任务Id获取任务", response = Object.class)
    public Object getInfo(@PathVariable String jobId) {
        return AjaxResult.successData(jobService.getById(jobId));
    }

    @PreAuthorize("hasAnyAuthority('quartz:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "新增/修改任务", response = Object.class)
    public Object edit(@Validated @RequestBody SysQuartzJob job) throws Exception {
        if (StringUtils.isEmpty(job.getId())) {
            if (jobService.checkUnique(job.getJobName()) != 0) {
                return AjaxResult.error("新增任务'" + job.getJobName() + "'失败，任务名称已存在");
            }
            job.setCreator(SecurityUtils.getUser().getId());
            jobService.insert(job);
        } else {
            job.setModifier(SecurityUtils.getUser().getId());
            jobService.update(job);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('quartz:delete')")
    @DeleteMapping("{jobIds}")
    @ApiOperation(value = "删除任务", response = Object.class)
    public Object remove(@PathVariable String[] jobIds) {
        jobService.deleteByIds(Arrays.asList(jobIds));
        return AjaxResult.success();
    }

    @PutMapping("changeStatus")
    @ApiOperation(value = "启停任务", response = Object.class)
    public Object changeStatus(@RequestBody SysQuartzJob job) throws Exception {
        SysQuartzJob newJob = jobService.getById(job.getId());
        newJob.setStatus(job.getStatus());
        newJob.setModifier(SecurityUtils.getUser().getId());
        jobService.changeStatus(newJob);
        return AjaxResult.success();
    }

    /**
     * 定时任务立即执行一次
     */
    @PutMapping("run")
    @ApiOperation(value = "执行任务", response = Object.class)
    public Object run(@RequestBody SysQuartzJob job) throws SchedulerException {
        SysQuartzJob newJob = jobService.getById(job.getId());
        jobService.run(newJob);
        return AjaxResult.success();
    }
}
