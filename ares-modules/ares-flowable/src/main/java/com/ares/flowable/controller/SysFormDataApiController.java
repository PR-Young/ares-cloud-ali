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
import com.ares.core.utils.StringUtils;
import com.ares.flowable.model.query.SysFormDataQuery;
import com.ares.flowable.persistence.model.SysFormData;
import com.ares.flowable.persistence.service.SysFormDataService;
import com.ares.security.common.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sysFormData/*")
@Api(value = "API", tags = {"管理"})
public class SysFormDataApiController extends BaseController {

    private SysFormDataService sysFormDataService;

    @Autowired
    public SysFormDataApiController(SysFormDataService sysFormDataService) {
        this.sysFormDataService = sysFormDataService;
    }

    @PreAuthorize("hasAnyAuthority('sysFormData:list')")
    @RequestMapping("list")
    @ApiOperation(value = "列表", response = TableDataInfo.class)
    public TableDataInfo list(SysFormDataQuery sysFormData) {
        startPage();
        List<SysFormData> sysFormDataList = sysFormDataService.list(sysFormData);
        return getDataTable(sysFormDataList);
    }

    @GetMapping("{sysFormDataId}")
    @ApiOperation(value = "根据Id获取信息", response = Object.class)
    public Object getInfo(@PathVariable Long sysFormDataId) {
        return AjaxResult.successData(sysFormDataService.getById(sysFormDataId));
    }

    @PreAuthorize("hasAnyAuthority('sysFormData:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "编辑信息", response = Object.class)
    public Object edit(@Validated @RequestBody SysFormData sysFormData) throws Exception {
        if (StringUtils.isEmpty(sysFormData.getId())) {
            sysFormData.setCreator(SecurityUtils.getUser().getId());
            sysFormDataService.insert(sysFormData);
        } else {
            sysFormData.setModifier(SecurityUtils.getUser().getId());
            sysFormDataService.update(sysFormData);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysFormData:delete')")
    @DeleteMapping("{sysFormDataIds}")
    @ApiOperation(value = "删除信息", response = Object.class)
    public Object remove(@PathVariable Long[] sysFormDataIds) {
        sysFormDataService.deleteByIds(Arrays.asList(sysFormDataIds));
        return AjaxResult.success();
    }

    @GetMapping("getFormData/{proInstId}")
    @ApiOperation(value = "根据proInstId获取信息", response = Object.class)
    public Object getFormData(@PathVariable String proInstId) {
        return AjaxResult.successData(sysFormDataService.getFormDataByProInstId(proInstId));
    }
}
