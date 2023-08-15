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

package com.ares.quartz.persistence.model;

import com.ares.core.serializer.LongJsonDeserializer;
import com.ares.core.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysQuartzJobLog对象", description = "任务日志")
public class SysQuartzJobLog implements Serializable {
    @ApiModelProperty("主键")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;
    @ApiModelProperty("任务名称")
    private String jobName;
    @ApiModelProperty("任务分组")
    private String jobGroup;
    @ApiModelProperty("目标类")
    private String invokeTarget;
    @ApiModelProperty("任务消息")
    private String jobMessage;
    @ApiModelProperty("任务状态")
    private Integer status;
    @ApiModelProperty("异常信息")
    private String exceptionInfo;
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
    @ApiModelProperty("开始时间")
    private String beginTime;
    @ApiModelProperty("结束时间")
    private String endTime;

}
