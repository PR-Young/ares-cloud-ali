package com.ares.system.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysRoleService;
import com.ares.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "系统角色API", tags = {"系统角色"})
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
    @ApiOperation(value = "角色列表", response = TableDataInfo.class)
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> roleList = roleService.selectRoleList(role);
        return getDataTable(roleList);
    }

    @GetMapping("{roleId}")
    @ApiOperation(value = "根据角色Id获取用户", response = Object.class)
    public Object getInfo(@PathVariable String roleId) {
        return AjaxResult.successData(roleService.getById(roleId));
    }

    @PreAuthorize("hasAnyAuthority('role:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "新增/修改角色", response = Object.class)
    public Object edit(@Validated @RequestBody SysRole role) throws Exception {
        String roleId = "";
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
    @ApiOperation(value = "删除用户", response = Object.class)
    public Object remove(@PathVariable String[] roleIds) {
        roleService.deleteByIds(Arrays.asList(roleIds));
        return AjaxResult.success();
    }

    @PutMapping("dataScope")
    @ApiOperation(value = "角色权限分配", response = Object.class)
    public Object dataScope(@RequestBody SysRole role) {
        roleService.authDataScope(role);
        return AjaxResult.success();
    }

    @GetMapping("optionselect")
    @ApiOperation(value = "角色下拉选项", response = Object.class)
    public Object optionSelect() {
        return AjaxResult.successData(roleService.getAll());
    }

    @GetMapping("roleUserselect/{roleId}")
    @ApiOperation(value = "根据角色Id获取用户", response = Object.class)
    public Object roleUserselect(@PathVariable String roleId) {
        AjaxResult result = AjaxResult.success();
        result.put("allUser", userService.selectUserList(new SysUser()));
        result.put("checkedKeys", userService.getUserByRole(roleId));
        return result;
    }

    @GetMapping("getRoleByUserId/{userId}")
    public JsonResult<List<SysRole>> getRoleByUserId(@PathVariable("userId") String userId){
        return JsonResult.success(roleService.getRoleByUserId(userId));
    }

    @GetMapping("getPermsByRoleId/{roleId}")
    public JsonResult<List<String>> getPermsByRoleId(@PathVariable("roleId") String roleId){
        return JsonResult.success(roleService.getPermsByRoleId(roleId));
    }

    @GetMapping("getRoleById/{roleId}")
    public JsonResult<SysRole> getById(@PathVariable("roleId") String roleId){
        return JsonResult.success(roleService.getById(roleId));
    }

    @GetMapping("getAllRole")
    public JsonResult<List<SysRole>> getAll(){
        return JsonResult.success(roleService.getAll());
    }

    @RequestMapping("selectRoleList")
    public JsonResult<List<SysRole>> selectRoleList(SysRole role){
        return JsonResult.success(roleService.selectRoleList(role));
    }
}
