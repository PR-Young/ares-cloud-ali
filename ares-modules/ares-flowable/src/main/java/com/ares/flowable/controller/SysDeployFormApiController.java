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
import com.ares.flowable.model.query.SysDeployFormQuery;
import com.ares.flowable.persistence.model.SysDeployForm;
import com.ares.flowable.persistence.service.SysDeployFormService;
import com.ares.security.common.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sysDeployForm/*")
@Tag(name = "SysDeployFormApiController", description = "管理")
public class SysDeployFormApiController extends BaseController {

    private SysDeployFormService sysDeployFormService;

    @Autowired
    public SysDeployFormApiController(SysDeployFormService sysDeployFormService) {
        this.sysDeployFormService = sysDeployFormService;
    }

    @PreAuthorize("hasAnyAuthority('sysDeployForm:list')")
    @RequestMapping("list")
    @Operation(summary = "列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = TableDataInfo.class)))})
    public TableDataInfo list(SysDeployFormQuery sysDeployForm) {
        startPage();
        List<SysDeployForm> sysDeployFormList = sysDeployFormService.list(sysDeployForm);
        return getDataTable(sysDeployFormList);
    }

    @GetMapping("{sysDeployFormId}")
    @Operation(summary = "根据Id获取信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object getInfo(@PathVariable Long sysDeployFormId) {
        return AjaxResult.successData(sysDeployFormService.getById(sysDeployFormId));
    }

    @PreAuthorize("hasAnyAuthority('sysDeployForm:edit')")
    @PostMapping("edit")
    @Operation(summary = "编辑信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
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
    @Operation(summary = "删除信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object remove(@PathVariable Long[] sysDeployFormIds) {
        sysDeployFormService.deleteByIds(Arrays.asList(sysDeployFormIds));
        return AjaxResult.success();
    }
}
