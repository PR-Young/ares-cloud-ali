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
import com.ares.core.model.query.SysRoleQuery;
import com.ares.core.model.query.SysUserQuery;
import com.ares.core.model.system.SysRole;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysRoleService;
import com.ares.system.service.SysUserService;
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
 * @description: 角色
 * @author: Young 2020/05/06
 **/
@RestController
@RequestMapping("/system/role/*")
@Tag(name = "SysRoleApiController", description = "系统角色")
public class SysRoleApiController extends BaseController {
    private SysRoleService roleService;
    private SysUserService userService;

    @Autowired
    public SysRoleApiController(SysRoleService roleService, SysUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('role:list')")
    @RequestMapping("list")
    @Operation(summary = "角色列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = TableDataInfo.class)))})
    public TableDataInfo list(SysRoleQuery role) {
        startPage();
        List<SysRole> roleList = roleService.selectRoleList(role);
        return getDataTable(roleList);
    }

    @GetMapping("{roleId}")
    @Operation(summary = "根据角色Id获取用户", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object getInfo(@PathVariable Long roleId) {
        return AjaxResult.successData(roleService.getById(roleId));
    }

    @PreAuthorize("hasAnyAuthority('role:edit')")
    @PostMapping("edit")
    @Operation(summary = "新增/修改角色", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object edit(@Validated @RequestBody SysRole role) throws Exception {
        Long roleId = 0L;
        if (StringUtils.isEmpty(role.getId())) {
            if (!roleService.checkRoleName(role.getRoleName())) {
                return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
            }
            role.setCreator(SecurityUtils.getUser().getId());
            roleId = roleService.insertRole(role);
        } else {
            roleId = role.getId();
            role.setModifier(SecurityUtils.getUser().getId());
            roleService.updateRole(role);
        }

        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('role:delete')")
    @DeleteMapping("{roleIds}")
    @Operation(summary = "删除用户", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object remove(@PathVariable Long[] roleIds) {
        roleService.deleteByIds(Arrays.asList(roleIds));
        return AjaxResult.success();
    }

    @PutMapping("dataScope")
    @Operation(summary = "角色权限分配", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object dataScope(@RequestBody SysRole role) {
        roleService.authDataScope(role);
        return AjaxResult.success();
    }

    @GetMapping("optionselect")
    @Operation(summary = "角色下拉选项", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object optionSelect() {
        return AjaxResult.successData(roleService.getAll());
    }

    @GetMapping("roleUserselect/{roleId}")
    @Operation(summary = "根据角色Id获取用户", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object roleUserselect(@PathVariable Long roleId) {
        AjaxResult result = AjaxResult.success();
        result.put("allUser", userService.selectUserList(new SysUserQuery()));
        result.put("checkedKeys", userService.getUserByRole(roleId));
        return result;
    }

}
