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
import com.ares.core.model.system.SysDictData;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/system/dict/data/*")
@Api(value = "字典数据API", tags = {"字典数据"})
public class SysDictDataApiController extends BaseController {

    private SysDictDataService sysDictDataService;

    @Autowired
    public SysDictDataApiController(SysDictDataService sysDictDataService) {
        this.sysDictDataService = sysDictDataService;
    }

    @PreAuthorize("hasAnyAuthority('sysDictData:list')")
    @RequestMapping("list")
    @ApiOperation(value = "字典数据列表", response = TableDataInfo.class)
    public TableDataInfo list(SysDictData sysDictData) {
        startPage();
        List<SysDictData> sysDictDataList = sysDictDataService.list(sysDictData);
        return getDataTable(sysDictDataList);
    }

    @GetMapping("{sysDictDataId}")
    @ApiOperation(value = "根据Id获取字典数据", response = Object.class)
    public Object getInfo(@PathVariable Long sysDictDataId) {
        return AjaxResult.successData(sysDictDataService.getById(sysDictDataId));
    }

    @PreAuthorize("hasAnyAuthority('sysDictData:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "编辑字典数据", response = Object.class)
    public Object edit(@Validated @RequestBody SysDictData sysDictData) throws Exception {
        if (StringUtils.isEmpty(sysDictData.getId())) {
            sysDictData.setCreator(SecurityUtils.getUser().getId());
            sysDictDataService.insert(sysDictData);
        } else {
            sysDictData.setModifier(SecurityUtils.getUser().getId());
            sysDictDataService.update(sysDictData);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysDictData:delete')")
    @DeleteMapping("{sysDictDataIds}")
    @ApiOperation(value = "删除字典数据", response = Object.class)
    public Object remove(@PathVariable Long[] sysDictDataIds) {
        sysDictDataService.deleteByIds(Arrays.asList(sysDictDataIds));
        return AjaxResult.success();
    }

    @GetMapping("dictType/{dictType}")
    @ApiOperation(value = "根据类别获取字典数据", response = Object.class)
    public Object getDicts(@PathVariable String dictType) {
        return AjaxResult.successData(sysDictDataService.getDicts(dictType));
    }
}
