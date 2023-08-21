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


import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.query.SysMenuQuery;
import com.ares.core.model.system.SysMenu;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 菜单
 * @author: Young 2020/05/05
 **/
@RestController
@RequestMapping("/system/menu/*")
@Tag(name = "SysMenuApiController", description = "系统菜单")
public class SysMenuApiController {

    private SysMenuService menuService;

    @Autowired
    public SysMenuApiController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasAnyAuthority('menu:list')")
    @RequestMapping("list")
    @Operation(summary = "菜单列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object list(SysMenuQuery menu) throws Exception {
        Long userId = SecurityUtils.getUser().getId();
        List<SysMenu> menuList = menuService.selectMenuList(menu, userId);
        return AjaxResult.successData(menuList);
    }

    @GetMapping(value = "{menuId}")
    @Operation(summary = "根据菜单Id获取菜单", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object getInfo(@PathVariable Long menuId) {
        return AjaxResult.successData(menuService.getById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("treeselect")
    @Operation(summary = "获取菜单下拉树列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object treeselect(SysMenuQuery menu) throws Exception {
        Long userId = SecurityUtils.getUser().getId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.successData(menuService.buildMenuTreeSelect(menus));
    }

    @PreAuthorize("hasAnyAuthority('menu:edit')")
    @PostMapping("edit")
    @Operation(summary = "新增/修改菜单", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object edit(@Validated @RequestBody SysMenu menu) throws Exception {
        if (StringUtils.isEmpty(menu.getId())) {
            menu.setCreator(SecurityUtils.getUser().getId());
            menuService.insert(menu);
        } else {
            menu.setModifier(SecurityUtils.getUser().getId());
            menuService.update(menu);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('menu:delete')")
    @DeleteMapping("{menuId}")
    @Operation(summary = "删除菜单", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object remove(@PathVariable Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
        menuService.remove(menuId);
        return AjaxResult.success();
    }

    @GetMapping(value = "roleMenuTreeselect/{roleId}")
    @Operation(summary = "根据角色Id获取菜单", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object roleMenuTreeselect(@PathVariable("roleId") Long roleId) throws Exception {
        Long userId = SecurityUtils.getUser().getId();
        List<SysMenu> menus = menuService.selectMenuList(new SysMenuQuery(), userId);
        AjaxResult result = AjaxResult.success();
        result.put("checkedKeys", menuService.selectMenuByRole(roleId));
        result.put("menus", menuService.buildMenuTreeSelect(menus));
        return result;
    }
}
