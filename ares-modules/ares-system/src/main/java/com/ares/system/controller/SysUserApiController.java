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


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.Constants;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.query.SysUserQuery;
import com.ares.core.model.system.SysUser;
import com.ares.core.utils.ExcelUtils;
import com.ares.core.utils.StringUtils;
import com.ares.redis.utils.RedisUtil;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysPostService;
import com.ares.system.service.SysRoleService;
import com.ares.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 用户
 * @author: Young 2020/05/05
 **/
@RestController
@RequestMapping("/system/user/*")
@Api(value = "系统用户API", tags = {"系统用户"})
public class SysUserApiController extends BaseController {

    private SysUserService userService;
    private SysRoleService roleService;
    private SysPostService postService;

    @Autowired
    public SysUserApiController(SysUserService userService,
                                SysRoleService roleService,
                                SysPostService postService) {
        this.userService = userService;
        this.roleService = roleService;
        this.postService = postService;
    }

    @PreAuthorize("hasAnyAuthority('user:list')")
    @RequestMapping("list")
    @ApiOperation(value = "用户列表", response = TableDataInfo.class)
    public TableDataInfo list(SysUserQuery user) {
        startPage();
        List<SysUser> userList = userService.selectUserList(user);
        return getDataTable(userList);
    }

    @GetMapping(value = {"", "{userId}"})
    @ApiOperation(value = "根据用户Id获取用户", response = Object.class)
    public Object getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult result = new AjaxResult();
        result.put("code", HttpStatus.OK.value());
        result.put("data", userService.getById(userId));
        result.put("roles", roleService.getAll());
        result.put("posts", postService.getAll());
        if (StringUtils.isNotEmpty(userId)) {
            result.put("roleIds", roleService.getRoleIdsByUser(userId));
        }
        return result;
    }

    @PreAuthorize("hasAnyAuthority('user:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "新增/修改用户", response = Object.class)
    public Object edit(@Validated @RequestBody SysUser user) throws Exception {
        Long userId = 0L;
        if (StringUtils.isEmpty(user.getId())) {
            if (userService.checkAccount(user.getAccount()) != 0) {
                return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
            }
            user.setCreator(SecurityUtils.getUser().getId());
            userId = userService.insertUser(user);
        } else {
            userId = user.getId();
            user.setModifier(SecurityUtils.getUser().getId());
            userService.update(user);
        }
        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            roleService.saveRoleUser(user.getRoleIds(), userId);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("{userIds}")
    @ApiOperation(value = "删除用户", response = Object.class)
    public Object remove(@PathVariable Long[] userIds) {
        userService.deleteByIds(Arrays.asList(userIds));
        return AjaxResult.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("resetPwd")
    @ApiOperation(value = "重置密码", response = Object.class)
    public Object resetPwd(@RequestBody SysUser user) {
        userService.resetPassword(user.getId());
        return AjaxResult.success();
    }

    @RequestMapping("export")
    @ApiOperation(value = "导出用户")
    public void export(SysUserQuery user, HttpServletResponse response) {
        List<SysUser> userList = userService.selectUserList(user);
        String fileName = "用户信息" + System.currentTimeMillis();
        String sheetName = "用户";
        try {
            ExcelUtils.writeExcel(response, userList, fileName, sheetName, SysUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("importTemplate")
    @ApiOperation(value = "导入模版")
    public void importTemplate(HttpServletResponse response) {
        List<SysUser> userList = new ArrayList<>();
        String fileName = "用户信息模版" + System.currentTimeMillis();
        String sheetName = "用户";
        try {
            ExcelUtils.writeExcel(response, userList, fileName, sheetName, SysUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("importData")
    @PreAuthorize("hasAnyAuthority('user:import')")
    @ApiOperation(value = "导入用户")
    public Object importData(MultipartFile file, HttpServletRequest request) throws Exception {
        InputStream inputStream = file.getInputStream();
        boolean needUpdate = request.getParameter("updateSupport") == "1" ? true : false;
        Long deptId = Long.valueOf(request.getParameter("deptId"));
        AnalysisEventListener listener = userService.new UserDataListener(needUpdate, deptId);
        EasyExcel.read(inputStream, SysUser.class, listener).sheet().doRead();
        return AjaxResult.success("导入成功");
    }
	
	@RequestMapping("kick")
    @ApiOperation(value = "下线", response = Object.class)
    public Object kickUser(@RequestParam("username") String userName) {
        SysUser user = userService.getUserByName(userName);
        RedisUtil.del(Constants.LOGIN_INFO + userName);
        return AjaxResult.success();
    }

}
