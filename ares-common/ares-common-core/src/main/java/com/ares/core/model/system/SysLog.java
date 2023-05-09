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

package com.ares.core.model.system;


import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/27
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLog对象", description = "系统日志")
public class SysLog extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2925066346168762533L;
    @ApiModelProperty("主机IP")
    private String hostIp;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("url地址")
    private String url;
    @ApiModelProperty("操作参数")
    private String operParams;
    @ApiModelProperty("备注")
    private String notes;
    @ApiModelProperty("请求方式")
    private String requestMethod;
}
