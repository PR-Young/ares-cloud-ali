package com.ares.system.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.system.SysProperties;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysPropertiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 系统参数
 * @author: Young
 */
@RestController
@RequestMapping("/sysProperties/*")
@Api(value = "系统参数API", tags = {"系统参数"})
public class SysPropertiesApiController extends BaseController {

    private SysPropertiesService sysPropertiesService;

    @Autowired
    public SysPropertiesApiController(SysPropertiesService sysPropertiesService) {
        this.sysPropertiesService = sysPropertiesService;
    }

    @PreAuthorize("hasAnyAuthority('sysProperties:list')")
    @RequestMapping("list")
    @ApiOperation(value = "系统参数列表", response = TableDataInfo.class)
    public TableDataInfo list(SysProperties sysProperties) {
        startPage();
        List<SysProperties> sysPropertiesList = sysPropertiesService.list(sysProperties);
        return getDataTable(sysPropertiesList);
    }

    @GetMapping("{sysPropertiesId}")
    @ApiOperation(value = "根据参数Id获取系统参数", response = Object.class)
    public Object getInfo(@PathVariable String sysPropertiesId) {
        return AjaxResult.successData(sysPropertiesService.getById(sysPropertiesId));
    }

    @PreAuthorize("hasAnyAuthority('sysProperties:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "新增/修改系统参数", response = Object.class)
    public Object edit(@Validated @RequestBody SysProperties sysProperties) throws Exception {
        if (StringUtils.isEmpty(sysProperties.getId())) {
            sysProperties.setCreator(SecurityUtils.getUser().getId());
            sysPropertiesService.insert(sysProperties);
        } else {
            sysProperties.setModifier(SecurityUtils.getUser().getId());
            sysPropertiesService.update(sysProperties);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('sysProperties:delete')")
    @DeleteMapping("{sysPropertiesIds}")
    @ApiOperation(value = "删除系统参数", response = Object.class)
    public Object remove(@PathVariable String[] sysPropertiesIds) {
        sysPropertiesService.deleteByIds(Arrays.asList(sysPropertiesIds));
        return AjaxResult.success();
    }
}
