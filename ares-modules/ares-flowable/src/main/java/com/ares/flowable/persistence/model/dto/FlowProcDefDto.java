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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "流程定义")
public class FlowProcDefDto implements Serializable {

    @Schema(description = "流程id")
    private String id;

    @Schema(description = "流程名称")
    private String name;

    @Schema(description = "流程key")
    private String key;

    @Schema(description = "流程分类")
    private String category;

    @Schema(description = "配置表单名称")
    private String formName;

    @Schema(description = "配置表单id")
    private String formId;

    @Schema(description = "版本")
    private int version;

    @Schema(description = "部署ID")
    private String deploymentId;

    @Schema(description = "流程定义状态: 1:激活 , 2:中止")
    private int suspensionState;

    @Schema(description = "部署时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;


}
