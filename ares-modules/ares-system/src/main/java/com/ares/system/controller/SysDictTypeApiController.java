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
import com.ares.core.model.query.SysDictTypeQuery;
import com.ares.core.model.system.SysDictType;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysDictTypeService;
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
 * @description: 字典
 * @author: Young
 */
@RestController
@RequestMapping("/system/dict/type/*")
@Tag(name = "SysDictTypeApiController", description = "字典类别")
public class SysDictTypeApiController extends BaseController {

    private SysDictTypeService sysDictTypeService;

    @Autowired
    public SysDictTypeApiController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @PreAuthorize("hasAnyAuthority('sysDictType:list')")
    @RequestMapping("list")
    @Operation(summary = "字典类别列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = TableDataInfo.class)))})
    public TableDataInfo list(SysDictTypeQuery sysDictType) {
        startPage();
        List<SysDictType> sysDictTypeList = sysDictTypeService.list(sysDictType);
        return getDataTable(sysDictTypeList);
    }

    @GetMapping("{sysDictTypeId}")
    @Operation(summary = "根据Id获取字典类别", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object getInfo(@PathVariable Long sysDictTypeId) {
        return AjaxResult.successData(sysDictTypeService.getById(sysDictTypeId));
    }

    @PreAuthorize("hasAnyAuthority('sysDictType:edit')")
    @PostMapping("edit")
    @Operation(summary = "编辑字典类别", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object edit(@Validated @RequestBody SysDictType sysDictType) throws Exception {
        if (StringUtils.isEmpty(sysDictType.getId())) {
            sysDictType.setCreator(SecurityUtils.getUser().getId());
            sysDictTypeService.insert(sysDictType);
        } else {
            sysDictType.setModifier(SecurityUtils.getUser().getId());
            sysDictTypeService.update(sysDictType);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysDictType:delete')")
    @DeleteMapping("{sysDictTypeIds}")
    @Operation(summary = "删除字典类别", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object remove(@PathVariable Long[] sysDictTypeIds) {
        sysDictTypeService.deleteByIds(Arrays.asList(sysDictTypeIds));
        return AjaxResult.success();
    }

}
