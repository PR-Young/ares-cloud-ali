/*
 *
 *  * !******************************************************************************
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

import java.util.Date;

@Data
@Schema(title = "SysLoginInfo对象", description = "")
public class SysLoginInfo extends BaseModel {
    @Schema(description = "")
    private String browser;
    @Schema(description = "")
    private String ipAddr;
    @Schema(description = "")
    private String loginLocation;
    @Schema(description = "")
    private Date loginTime;
    @Schema(description = "")
    private String msg;
    @Schema(description = "")
    private String os;
    @Schema(description = "")
    private String status;
    @Schema(description = "")
    private String userName;
}
