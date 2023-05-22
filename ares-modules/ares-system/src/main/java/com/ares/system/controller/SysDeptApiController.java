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
import com.ares.core.model.system.SysDept;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 部门
 * @author: Young
 */
@RestController
@RequestMapping("/sysDept/*")
@Api(value = "部门管理API", tags = {"部门管理"})
public class SysDeptApiController extends BaseController {

    private SysDeptService sysDeptService;

    @Autowired
    public SysDeptApiController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @PreAuthorize("hasAnyAuthority('sysDept:list')")
    @RequestMapping("list")
    @ApiOperation(value = "部门列表", response = TableDataInfo.class)
    public TableDataInfo list(SysDept sysDept) {
        startPage();
        List<SysDept> sysDeptList = sysDeptService.list(sysDept);
        return getDataTable(sysDeptList);
    }

    @GetMapping("{sysDeptId}")
    @ApiOperation(value = "根据Id获取部门信息", response = Object.class)
    public Object getInfo(@PathVariable String sysDeptId) {
        return AjaxResult.successData(sysDeptService.getByDeptId(sysDeptId));
    }

    //@PreAuthorize("hasAnyAuthority('sysDept:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "编辑部门信息", response = Object.class)
    public Object edit(@Validated @RequestBody SysDept sysDept) throws Exception {
        if (StringUtils.isEmpty(sysDept.getId())) {
            sysDept.setCreator(SecurityUtils.getUser().getId());
            sysDeptService.insert(sysDept);
        } else {
            sysDept.setModifier(SecurityUtils.getUser().getId());
            sysDeptService.update(sysDept);
        }
        return AjaxResult.success();
    }

    //@PreAuthorize("hasAnyAuthority('sysDept:delete')")
    @DeleteMapping("{sysDeptIds}")
    @ApiOperation(value = "删除部门", response = Object.class)
    public Object remove(@PathVariable String[] sysDeptIds) {
        sysDeptService.deleteByIds(Arrays.asList(sysDeptIds));
        return AjaxResult.success();
    }

    @RequestMapping("treeselect")
    @ApiOperation(value = "部门树列表", response = Object.class)
    public Object tree(HttpServletRequest request, HttpServletResponse response) {
        return AjaxResult.successData(sysDeptService.buildDeptTree());
    }

}
