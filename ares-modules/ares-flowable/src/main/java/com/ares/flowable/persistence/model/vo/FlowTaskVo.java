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

package com.ares.flowable.persistence.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2022/1/13
 * @see: com.ares.flowable.persistence.model.vo.FlowTaskVo.java
 **/
@Data
@Schema(description = "工作流任务相关--请求参数")
public class FlowTaskVo {

    @Schema(description = "任务Id")
    private String taskId;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "任务意见")
    private String comment;

    @Schema(description = "流程实例Id")
    private String instanceId;

    @Schema(description = "节点")
    private String targetKey;

    @Schema(description = "流程变量信息")
    private Map<String, Object> values;

    @Schema(description = "审批人")
    private String assignee;

    @Schema(description = "候选人")
    private List<String> candidateUsers;

    @Schema(description = "审批组")
    private List<String> candidateGroups;

}
