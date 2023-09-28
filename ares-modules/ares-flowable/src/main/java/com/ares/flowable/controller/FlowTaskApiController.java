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


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.flowable.persistence.model.dto.FlowTaskDto;
import com.ares.flowable.persistence.model.vo.FlowTaskVo;
import com.ares.flowable.persistence.service.FlowDefinitionService;
import com.ares.flowable.persistence.service.FlowTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Tag(name = "FlowTaskApiController", description = "工作流流程任务管理")
@RestController
@RequestMapping("/flowable/task")
public class FlowTaskApiController extends BaseController {

    private FlowTaskService flowTaskService;
    private FlowDefinitionService flowDefinitionService;

    @Autowired
    public FlowTaskApiController(FlowTaskService flowTaskService,
                                 FlowDefinitionService flowDefinitionService) {
        this.flowTaskService = flowTaskService;
        this.flowDefinitionService = flowDefinitionService;
    }

    @Operation(summary = "我发起的流程", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @GetMapping(value = "/myProcess")
    public Object myProcess(@Parameter(description = "当前页码", required = true) @RequestParam Integer pageNum,
                            @Parameter(description = "每页条数", required = true) @RequestParam Integer pageSize) {
        return getDataTable(flowTaskService.myProcess(pageNum, pageSize));
    }

    @Operation(summary = "取消申请", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @PostMapping(value = "/stopProcess")
    public AjaxResult stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.stopProcess(flowTaskVo);
    }

    @Operation(summary = "撤回流程", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @PostMapping(value = "/revokeProcess")
    public AjaxResult revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.revokeProcess(flowTaskVo);
    }

    @Operation(summary = "获取待办列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @GetMapping(value = "/todoList")
    public Object todoList(@Parameter(description = "当前页码", required = true) @RequestParam Integer pageNum,
                           @Parameter(description = "每页条数", required = true) @RequestParam Integer pageSize) {
        return getDataTable(flowTaskService.todoList(pageNum, pageSize));
    }

    @Operation(summary = "获取已办任务", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @GetMapping(value = "/finishedList")
    public Object finishedList(@Parameter(description = "当前页码", required = true) @RequestParam Integer pageNum,
                               @Parameter(description = "每页条数", required = true) @RequestParam Integer pageSize) {
        return getDataTable(flowTaskService.finishedList(pageNum, pageSize));
    }


    @Operation(summary = "流程历史流转记录", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @GetMapping(value = "/flowRecord")
    public AjaxResult flowRecord(String procInsId, String deployId) {
        return flowTaskService.flowRecord(procInsId, deployId);
    }

    @Operation(summary = "获取流程变量", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FlowTaskDto.class)))})
    @GetMapping(value = "/processVariables/{taskId}")
    public AjaxResult processVariables(@Parameter(description = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }

    @Operation(summary = "审批任务")
    @PostMapping(value = "/complete")
    public AjaxResult complete(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.complete(flowTaskVo);
    }

    @Operation(summary = "驳回任务")
    @PostMapping(value = "/rejectnew")
    public AjaxResult taskRejectNew(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskRejectNew(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "驳回任务")
    @PostMapping(value = "/reject")
    public AjaxResult taskReject(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReject(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "退回任务")
    @PostMapping(value = "/return")
    public AjaxResult taskReturn(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReturn(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "获取所有可回退的节点")
    @PostMapping(value = "/returnList")
    public AjaxResult findReturnTaskList(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.findReturnTaskList(flowTaskVo);
    }

    @Operation(summary = "删除任务")
    @DeleteMapping(value = "/delete")
    public AjaxResult delete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.deleteTask(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "认领/签收任务")
    @PostMapping(value = "/claim")
    public AjaxResult claim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.claim(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "取消认领/签收任务")
    @PostMapping(value = "/unClaim")
    public AjaxResult unClaim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.unClaim(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "委派任务")
    @PostMapping(value = "/delegate")
    public AjaxResult delegate(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.delegateTask(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "转办任务")
    @PostMapping(value = "/assign")
    public AjaxResult assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo);
        return AjaxResult.success();
    }

    @Operation(summary = "获取下一节点")
    @PostMapping(value = "/nextFlowNode")
    public AjaxResult getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.getNextFlowNode(flowTaskVo);
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成流程图
     *
     * @param procInsId 任务ID
     */
    @RequestMapping("/flowViewer/{procInsId}")
    public AjaxResult getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return flowTaskService.getFlowViewer(procInsId);
    }

    @GetMapping(value = "/applyFlowList")
    @Operation(summary = "流程列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object list(@Parameter(description = "当前页码", required = true) @RequestParam Integer pageNum,
                       @Parameter(description = "每页条数", required = true) @RequestParam Integer pageSize) {

        return getDataTable(flowDefinitionService.applyFlowList(pageNum, pageSize));
    }

    @GetMapping(value = "/activeFlowList")
    @Operation(summary = "流程列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object list() {
        return AjaxResult.successData(flowDefinitionService.applyFlowList());
    }
}
