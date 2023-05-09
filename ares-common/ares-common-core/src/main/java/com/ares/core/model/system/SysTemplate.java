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

@Data
@ApiModel(value = "SysTemplate对象", description = "系统模版")
public class SysTemplate extends BaseModel {
    private static final long serialVersionUID = 1595294527563638343L;
    @ApiModelProperty("模版名称")
    private String name;
    @ApiModelProperty("模版类别")
    private String subject;
    @ApiModelProperty("模版内容")
    private String text;
    @ApiModelProperty("html内容")
    private String html;
    @ApiModelProperty("模版参数")
    private String param;
}
