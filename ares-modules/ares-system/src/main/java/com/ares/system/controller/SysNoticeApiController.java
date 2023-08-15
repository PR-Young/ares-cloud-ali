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
import com.ares.core.exception.UserException;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.system.SysNotice;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysNoticeService;
import com.ares.system.websocket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 通知
 * @author: Young
 */
@RestController
@RequestMapping("/system/notice/*")
@Api(value = "通知公告API", tags = {"通知公告"})
public class SysNoticeApiController extends BaseController {

    private SysNoticeService sysNoticeService;

    @Autowired
    public SysNoticeApiController(SysNoticeService sysNoticeService) {
        this.sysNoticeService = sysNoticeService;
    }

    @PreAuthorize("hasAnyAuthority('notice:list')")
    @RequestMapping("list")
    @ApiOperation(value = "通知公告列表", response = TableDataInfo.class)
    public TableDataInfo list(SysNotice sysNotice) {
        startPage();
        List<SysNotice> sysNoticeList = sysNoticeService.list(sysNotice);
        return getDataTable(sysNoticeList);
    }

    @GetMapping("{noticeId}")
    @ApiOperation(value = "根据Id获取通知公告", response = Object.class)
    public Object getInfo(@PathVariable Long noticeId) {
        return AjaxResult.successData(sysNoticeService.getById(noticeId));
    }

    @PreAuthorize("hasAnyAuthority('notice:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "新增/修改通知公告", response = Object.class)
    public Object edit(@Validated @RequestBody SysNotice sysNotice) throws Exception {
        if (StringUtils.isEmpty(sysNotice.getId())) {
            sysNotice.setCreator(SecurityUtils.getUser().getId());
            sysNoticeService.insert(sysNotice);
        } else {
            sysNotice.setModifier(SecurityUtils.getUser().getId());
            sysNoticeService.update(sysNotice);
        }
        WebSocketServer.sendNotice(true, SecurityUtils.getUser().getAccount());
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('notice:delete')")
    @DeleteMapping("{noticeIds}")
    @ApiOperation(value = "删除通知公告", response = Object.class)
    public Object remove(@PathVariable Long[] noticeIds) {
        sysNoticeService.deleteByIds(Arrays.asList(noticeIds));
        return AjaxResult.success();
    }

    @GetMapping("noticeNum")
    @ApiOperation(value = "获取通知公告数量", response = Object.class)
    public Object noticeNum() throws UserException {
        return AjaxResult.successData(sysNoticeService.noticeNum(SecurityUtils.getUser().getId()));
    }

    @GetMapping("getNotices")
    @ApiOperation(value = "通知公告时间线", response = Object.class)
    public Object getNotices() throws Exception {
        WebSocketServer.sendInfo(String.valueOf(0), SecurityUtils.getUser().getAccount());
        return AjaxResult.successData(sysNoticeService.getNotices(SecurityUtils.getUser().getId()));
    }
}
