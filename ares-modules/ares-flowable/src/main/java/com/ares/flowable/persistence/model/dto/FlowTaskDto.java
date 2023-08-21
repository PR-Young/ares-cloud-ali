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

package com.ares.flowable.persistence.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Schema(description = "工作流任务相关-返回参数")
public class FlowTaskDto implements Serializable {

    @Schema(description = "任务编号")
    private String taskId;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务Key")
    private String taskDefKey;

    @Schema(description = "任务执行人Id")
    private String assigneeId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "流程发起人部门名称")
    private String startDeptName;

    @Schema(description = "任务执行人名称")
    private String assigneeName;

    @Schema(description = "流程发起人Id")
    private String startUserId;

    @Schema(description = "流程发起人名称")
    private String startUserName;

    @Schema(description = "流程类型")
    private String category;

    @Schema(description = "流程变量信息")
    private Object procVars;

    @Schema(description = "局部变量信息")
    private Object taskLocalVars;

    @Schema(description = "流程部署编号")
    private String deployId;

    @Schema(description = "流程ID")
    private String procDefId;

    @Schema(description = "流程key")
    private String procDefKey;

    @Schema(description = "流程定义名称")
    private String procDefName;

    @Schema(description = "流程定义内置使用版本")
    private int procDefVersion;

    @Schema(description = "流程实例ID")
    private String procInsId;

    @Schema(description = "历史流程实例ID")
    private String hisProcInsId;

    @Schema(description = "任务耗时")
    private String duration;

    @Schema(description = "任务意见")
    private FlowCommentDto comment;

    @Schema(description = "候选执行人")
    private String candidate;

    @Schema(description = "任务创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "任务完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

}
