package com.ares.system.controller;

import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.system.SysPost;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 职务
 * @author: Young
 */
@RestController
@RequestMapping("/sysPost/*")
@Api(value = "岗位管理API", tags = {"岗位管理"})
public class SysPostApiController extends BaseController {

    private SysPostService sysPostService;

    @Autowired
    public SysPostApiController(SysPostService sysPostService) {
        this.sysPostService = sysPostService;
    }

    @PreAuthorize("hasAnyAuthority('sysPost:list')")
    @RequestMapping("list")
    @ApiOperation(value = "岗位列表", response = TableDataInfo.class)
    public TableDataInfo list(SysPost sysPost) {
        startPage();
        List<SysPost> sysPostList = sysPostService.list(sysPost);
        return getDataTable(sysPostList);
    }

    @GetMapping("{sysPostId}")
    @ApiOperation(value = "根据Id获取岗位信息", response = Object.class)
    public Object getInfo(@PathVariable String sysPostId) {
        return AjaxResult.successData(sysPostService.getById(sysPostId));
    }

    @PreAuthorize("hasAnyAuthority('sysPost:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "编辑岗位信息", response = Object.class)
    public Object edit(@Validated @RequestBody SysPost sysPost) throws Exception {
        if (StringUtils.isEmpty(sysPost.getId())) {
            sysPost.setCreator(SecurityUtils.getUser().getId());
            sysPostService.insert(sysPost);
        } else {
            sysPost.setModifier(SecurityUtils.getUser().getId());
            sysPostService.update(sysPost);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysPost:delete')")
    @DeleteMapping("{sysPostIds}")
    @ApiOperation(value = "删除岗位信息", response = Object.class)
    public Object remove(@PathVariable String[] sysPostIds) {
        sysPostService.deleteByIds(Arrays.asList(sysPostIds));
        return AjaxResult.success();
    }

    @GetMapping("optionselect")
    @ApiOperation(value = "岗位下拉数据", response = Object.class)
    public Object optionSelect() {
        return AjaxResult.successData(sysPostService.getAll());
    }
}
