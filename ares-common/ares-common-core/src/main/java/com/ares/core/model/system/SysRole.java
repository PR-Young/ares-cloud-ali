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

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/25
 **/
@Data
@Schema(name = "SysRole对象", description = "系统角色")
public class SysRole extends BaseModel implements Serializable {

    private static final long serialVersionUID = 3066031349080678671L;
    @Schema(description = "角色名称")
    private String roleName;
    @Schema(description = "角色描述")
    private String description;

    /**
     * 菜单组
     */
    private String[] menuIds;

    private String[] userIds;
}
