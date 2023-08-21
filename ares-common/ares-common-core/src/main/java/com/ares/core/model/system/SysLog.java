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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/27
 **/
@Data
@Accessors(chain = true)
@Schema(name = "SysLog对象", description = "系统日志")
public class SysLog extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2925066346168762533L;
    @Schema(description = "主机IP")
    private String hostIp;
    @Schema(description = "用户名")
    private String userName;
    @Schema(description = "url地址")
    private String url;
    @Schema(description = "操作参数")
    private String operParams;
    @Schema(description = "备注")
    private String notes;
    @Schema(description = "请求方式")
    private String requestMethod;
}
