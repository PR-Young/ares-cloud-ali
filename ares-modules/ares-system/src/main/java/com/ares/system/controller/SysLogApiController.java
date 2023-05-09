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

package com.ares.system.controller;

import com.ares.core.controller.BaseController;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.system.SysLog;
import com.ares.system.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/1/10
 * @see: com.ares.system.controller.SysLogApiController.java
 **/
@RequestMapping("/system/log/*")
@RestController
public class SysLogApiController extends BaseController {

    private SysLogService sysLogService;

    @Autowired
    public SysLogApiController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @PreAuthorize("hasAnyAuthority('log:list')")
    @RequestMapping("list")
    @ApiOperation(value = "操作日志列表", response = TableDataInfo.class)
    public TableDataInfo list(SysLog sysLog) {
        startPage();
        List<SysLog> sysLogList = sysLogService.list(sysLog);
        return getDataTable(sysLogList);
    }
}
