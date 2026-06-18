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

package com.ares.flow.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.flow.model.FlowTaskVO;
import com.ares.flow.model.query.FlowTaskQuery;
import com.ares.flow.persistence.service.FlowTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.service.ChartService;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.orm.entity.FlowTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "FlowTaskApiController", description = "工作流流程任务管理")
@RestController
@RequestMapping("/aresflow/task")
public class FlowTaskApiController extends BaseController {

    private TaskService taskService;
    private FlowTaskService flowTaskService;
    private InsService insService;
    private ChartService chartService;

    @Autowired
    public FlowTaskApiController(TaskService taskService,
                              FlowTaskService flowTaskService,
                              InsService insService,
                              ChartService chartService) {
        this.taskService = taskService;
        this.flowTaskService = flowTaskService;
        this.insService = insService;
        this.chartService = chartService;
    }

    @Operation(summary = "获取我的所有任务")
    @GetMapping(value = "myProcess")
    public AjaxResult myProcess(FlowTaskQuery query) {
        startPage();
        return AjaxResult.successData(flowTaskService.myProcess(query));
    }

    @Operation(summary = "终止流程")
    @PostMapping(value = "termination/{taskId}")
    public AjaxResult termination(@PathVariable Long taskId, @RequestBody FlowParams params) {
        taskService.termination(taskId, params);
        return AjaxResult.success();
    }

    @Operation(summary = "撤销流程")
    @PostMapping(value = "revoke/{instanceId}")
    public AjaxResult revoke(@PathVariable Long instanceId, @RequestBody FlowParams params) {
        taskService.revoke(instanceId, params);
        return AjaxResult.success();
    }

    @Operation(summary = "获取待办列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTask.class)))})
    @GetMapping(value = "todoList")
    public AjaxResult todoList(FlowTaskQuery query) {
        startPage();
        return AjaxResult.successData(flowTaskService.todoList(query));
    }

    @Operation(summary = "获取已办任务", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTask.class)))})
    @GetMapping(value = "finishedList")
    public AjaxResult finishedList(FlowTaskQuery query) {
        startPage();
        return AjaxResult.successData(flowTaskService.finishedList(query));
    }


    @GetMapping(value = "flowRecord")
    public AjaxResult flowRecord(String procInsId, String deployId) {
        return flowTaskService.flowRecord(procInsId, deployId);
    }

    @Operation(summary = "流程通过")
    @PostMapping(value = "pass")
    public AjaxResult pass(@RequestBody FlowTaskVO flowTaskVO) {
        taskService.pass(flowTaskVO.getTaskId(), flowTaskVO.getMessage(), flowTaskVO.getVariable());
        return AjaxResult.success();
    }

    @Operation(summary = "流程退回")
    @PostMapping(value = "reject")
    public AjaxResult taskReject(@RequestBody FlowTaskVO flowTaskVO) {
        taskService.reject(flowTaskVO.getTaskId(), flowTaskVO.getMessage(), flowTaskVO.getVariable());
        return AjaxResult.success();
    }

    @Operation(summary = "流程跳过")
    @PostMapping(value = "skip")
    public AjaxResult skip(@RequestBody FlowTaskVO flowTaskVO) {
        taskService.skip(flowTaskVO.getTaskId(), flowTaskVO.getParams());
        return AjaxResult.success();
    }

    @Operation(summary = "转办")
    @PostMapping(value = "transfer")
    public AjaxResult transfer(@RequestBody FlowTaskVO flowTaskVO) {
        taskService.transfer(flowTaskVO.getTaskId(), flowTaskVO.getParams());
        return AjaxResult.success();
    }

    @Operation(summary = "委派")
    @PostMapping(value = "depute")
    public AjaxResult depute(@RequestBody FlowTaskVO flowTaskVO) {
        taskService.depute(flowTaskVO.getTaskId(), flowTaskVO.getParams());
        return AjaxResult.success();
    }
}
