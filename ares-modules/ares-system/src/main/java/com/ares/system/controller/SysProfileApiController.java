package com.ares.system.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.system.SysDept;
import com.ares.core.model.system.SysPost;
import com.ares.core.model.system.SysUser;
import com.ares.core.service.SysUserService;
import com.ares.core.utils.EncryptUtils;
import com.ares.core.utils.MD5Util;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysDeptService;
import com.ares.system.service.SysPostService;
import com.ares.system.service.SysPropertiesService;
import com.ares.system.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @description: 个人信息 业务处理
 * @author: Young
 */
@RestController
@RequestMapping("/system/user/profile/*")
@Api(value = "个人信息API", tags = {"个人信息"})
public class SysProfileApiController extends BaseController {
    private SysUserService userService;
    private UploadService uploadService;
    private SysPropertiesService propertiesService;
    private SysPostService postService;
    private SysDeptService deptService;

    @Autowired
    public SysProfileApiController(SysUserService userService,
                                   UploadService uploadService,
                                   SysPropertiesService propertiesService,
                                   SysPostService postService,
                                   SysDeptService deptService) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.propertiesService = propertiesService;
        this.postService = postService;
        this.deptService = deptService;
    }

    /**
     * 个人信息
     */
    @GetMapping("info")
    @ApiOperation(value = "获取个人信息", response = Object.class)
    public Object profile() throws Exception {
        SysUser user = SecurityUtils.getUser();
        SysDept sysDept = deptService.getById(user.getDeptId());
        SysPost sysPost = postService.getById(user.getPostId());
        AjaxResult result = AjaxResult.successData(user);
        result.put("roleGroup", userService.selectUserRoleGroup(user.getId()));
        result.put("deptGroup", null != sysDept ? sysDept.getDeptName() : "");
        result.put("postGroup", null != sysPost ? sysPost.getPostName() : "");
        return result;
    }

    /**
     * 修改用户
     */
    @PutMapping("update")
    @ApiOperation(value = "修改用户信息", response = Object.class)
    public Object updateProfile(@RequestBody SysUser user) {
        userService.update(user);
        return AjaxResult.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("updatePwd")
    @ApiOperation(value = "重置密码", response = Object.class)
    public Object updatePwd(String oldPassword, String newPassword) throws Exception {
        SysUser user = SecurityUtils.getUser();
        if (!user.getPassword().equals(MD5Util.encode(oldPassword))) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (user.getPassword().equals(MD5Util.encode(newPassword))) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (userService.updatePassword(user, newPassword) > 0) {
            user.setPassword(MD5Util.encode(newPassword));
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @PostMapping("avatar")
    @ApiOperation(value = "头像上传", response = Object.class)
    public Object avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            SysUser user = SecurityUtils.getUser();
            String path = propertiesService.getValueByAlias("avatar.path");
            String avatar = uploadService.upload(path, file);
            user.setAvatar(EncryptUtils.encode(avatar));
            userService.update(user);
            return AjaxResult.success().put("imgUrl", EncryptUtils.encode(avatar));
        }
        return AjaxResult.error("上传图片异常，请联系管理员!");
    }

    @GetMapping("{path}")
    @ApiOperation(value = "获取头像")
    public void getAvatar(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws Exception {
        File file = new File(EncryptUtils.decode(path));
        OutputStream toClient = response.getOutputStream();
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            int i = fileInputStream.available();
            byte data[] = new byte[i];
            fileInputStream.read(data);
            fileInputStream.close();
            response.setContentType("image/*");
            toClient.write(data);
        } else {
            toClient.write(null);
        }
        toClient.close();
    }
}
