package com.ares.system.controller;

import com.ares.core.controller.BaseController;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.system.SysLog;
import com.ares.system.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/1/10
 * @see: com.ares.system.controller.SysLogApiController.java
 **/
@RequestMapping("/system/log/*")
@RestController
public class SysLogApiController extends BaseController {

    private SysLogService sysLogService;

    @Autowired
    public SysLogApiController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @PreAuthorize("hasAnyAuthority('log:list')")
    @RequestMapping("list")
    @ApiOperation(value = "操作日志列表", response = TableDataInfo.class)
    public TableDataInfo list(SysLog sysLog) {
        startPage();
        List<SysLog> sysLogList = sysLogService.list(sysLog);
        return getDataTable(sysLogList);
    }
}
