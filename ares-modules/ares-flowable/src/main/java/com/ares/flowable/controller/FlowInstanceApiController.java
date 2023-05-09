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

package com.ares.flowable.controller;


import com.ares.core.model.base.AjaxResult;
import com.ares.flowable.persistence.model.vo.FlowTaskVo;
import com.ares.flowable.persistence.service.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "工作流流程实例管理")
@RestController
@RequestMapping("/flowable/instance")
public class FlowInstanceApiController {

    private FlowInstanceService flowInstanceService;

    @Autowired
    public FlowInstanceApiController(FlowInstanceService flowInstanceService) {
        this.flowInstanceService = flowInstanceService;
    }

    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/startBy/{procDefId}")
    public AjaxResult startById(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                                @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowInstanceService.startProcessInstanceById(procDefId, variables);
    }


    @ApiOperation(value = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public AjaxResult updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                  @ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId) {
        flowInstanceService.updateState(state, instanceId);
        return AjaxResult.success();
    }

    @ApiOperation("结束流程实例")
    @PostMapping(value = "/stopProcessInstance")
    public AjaxResult stopProcessInstance(@RequestBody FlowTaskVo flowTaskVo) {
        flowInstanceService.stopProcessInstance(flowTaskVo);
        return AjaxResult.success();
    }

    @ApiOperation(value = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public AjaxResult delete(@ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId,
                             @ApiParam(value = "删除原因") @RequestParam(required = false) String deleteReason) {
        flowInstanceService.delete(instanceId, deleteReason);
        return AjaxResult.success();
    }
}
