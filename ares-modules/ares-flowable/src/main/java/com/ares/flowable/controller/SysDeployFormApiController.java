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

package com.ares.flowable.controller;

import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.flowable.persistence.model.SysDeployForm;
import com.ares.flowable.persistence.service.SysDeployFormService;
import com.ares.security.common.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sysDeployForm/*")
@Api(value = "API", tags = {"管理"})
public class SysDeployFormApiController extends BaseController {

    private SysDeployFormService sysDeployFormService;

    @Autowired
    public SysDeployFormApiController(SysDeployFormService sysDeployFormService) {
        this.sysDeployFormService = sysDeployFormService;
    }

    @PreAuthorize("hasAnyAuthority('sysDeployForm:list')")
    @RequestMapping("list")
    @ApiOperation(value = "列表", response = TableDataInfo.class)
    public TableDataInfo list(SysDeployForm sysDeployForm) {
        startPage();
        List<SysDeployForm> sysDeployFormList = sysDeployFormService.list(sysDeployForm);
        return getDataTable(sysDeployFormList);
    }

    @GetMapping("{sysDeployFormId}")
    @ApiOperation(value = "根据Id获取信息", response = Object.class)
    public Object getInfo(@PathVariable String sysDeployFormId) {
        return AjaxResult.successData(sysDeployFormService.getById(sysDeployFormId));
    }

    @PreAuthorize("hasAnyAuthority('sysDeployForm:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "编辑信息", response = Object.class)
    public Object edit(@Validated @RequestBody SysDeployForm sysDeployForm) throws Exception {
        if (StringUtils.isEmpty(sysDeployForm.getId())) {
            sysDeployForm.setCreator(SecurityUtils.getUser().getId());
            sysDeployFormService.insert(sysDeployForm);
        } else {
            sysDeployForm.setModifier(SecurityUtils.getUser().getId());
            sysDeployFormService.update(sysDeployForm);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysDeployForm:delete')")
    @DeleteMapping("{sysDeployFormIds}")
    @ApiOperation(value = "删除信息", response = Object.class)
    public Object remove(@PathVariable String[] sysDeployFormIds) {
        sysDeployFormService.deleteByIds(Arrays.asList(sysDeployFormIds));
        return AjaxResult.success();
    }
}
