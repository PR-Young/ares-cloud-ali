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
import com.ares.core.model.system.SysMenu;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "系统菜单API", tags = {"系统菜单"})
public class SysMenuApiController {

    private SysMenuService menuService;

    @Autowired
    public SysMenuApiController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasAnyAuthority('menu:list')")
    @RequestMapping("list")
    @ApiOperation(value = "菜单列表", response = Object.class)
    public Object list(SysMenu menu) throws Exception {
        String userId = SecurityUtils.getUser().getId();
        List<SysMenu> menuList = menuService.selectMenuList(menu, userId);
        return AjaxResult.successData(menuList);
    }

    @GetMapping(value = "{menuId}")
    @ApiOperation(value = "根据菜单Id获取菜单", response = Object.class)
    public Object getInfo(@PathVariable String menuId) {
        return AjaxResult.successData(menuService.getById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("treeselect")
    @ApiOperation(value = "获取菜单下拉树列表", response = Object.class)
    public Object treeselect(SysMenu menu) throws Exception {
        String userId = SecurityUtils.getUser().getId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.successData(menuService.buildMenuTreeSelect(menus));
    }

    @PreAuthorize("hasAnyAuthority('menu:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "新增/修改菜单", response = Object.class)
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
    @ApiOperation(value = "删除菜单", response = Object.class)
    public Object remove(@PathVariable String menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
        menuService.remove(menuId);
        return AjaxResult.success();
    }

    @GetMapping(value = "roleMenuTreeselect/{roleId}")
    @ApiOperation(value = "根据角色Id获取菜单", response = Object.class)
    public Object roleMenuTreeselect(@PathVariable("roleId") String roleId) throws Exception {
        String userId = SecurityUtils.getUser().getId();
        List<SysMenu> menus = menuService.selectMenuList(new SysMenu(), userId);
        AjaxResult result = AjaxResult.success();
        result.put("checkedKeys", menuService.selectMenuByRole(roleId));
        result.put("menus", menuService.buildMenuTreeSelect(menus));
        return result;
    }
}
