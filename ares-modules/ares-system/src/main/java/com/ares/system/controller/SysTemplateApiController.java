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
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.query.SysTemplateQuery;
import com.ares.core.model.system.SysTemplate;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysTemplateService;
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

/**
 * @description: 模版
 * @author: Young
 */
@RestController
@RequestMapping("/sysTemplate/*")
@Tag(name = "SysTemplateApiController", description = "系统模版")
public class SysTemplateApiController extends BaseController {

    private SysTemplateService sysTemplateService;

    @Autowired
    public SysTemplateApiController(SysTemplateService sysTemplateService) {
        this.sysTemplateService = sysTemplateService;
    }

    @PreAuthorize("hasAnyAuthority('sysTemplate:list')")
    @RequestMapping("list")
    @Operation(summary = "模版列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = TableDataInfo.class)))})
    public TableDataInfo list(SysTemplateQuery sysTemplate) {
        startPage();
        List<SysTemplate> sysTemplateList = sysTemplateService.list(sysTemplate);
        return getDataTable(sysTemplateList);
    }

    @GetMapping("{sysTemplateId}")
    @Operation(summary = "根据模版Id获取模版", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object getInfo(@PathVariable Long sysTemplateId) {
        return AjaxResult.successData(sysTemplateService.getById(sysTemplateId));
    }

    @PreAuthorize("hasAnyAuthority('sysTemplate:edit')")
    @PostMapping("edit")
    @Operation(summary = "新增/修改模版", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object edit(@Validated @RequestBody SysTemplate sysTemplate) throws Exception {
        if (StringUtils.isEmpty(sysTemplate.getId())) {
            sysTemplate.setCreator(SecurityUtils.getUser().getId());
            sysTemplateService.insert(sysTemplate);
        } else {
            sysTemplate.setModifier(SecurityUtils.getUser().getId());
            sysTemplateService.update(sysTemplate);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysTemplate:delete')")
    @DeleteMapping("{sysTemplateIds}")
    @Operation(summary = "删除模版", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object remove(@PathVariable Long[] sysTemplateIds) {
        sysTemplateService.deleteByIds(Arrays.asList(sysTemplateIds));
        return AjaxResult.success();
    }
}
