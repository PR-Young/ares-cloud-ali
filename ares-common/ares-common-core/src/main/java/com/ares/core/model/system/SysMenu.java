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
import com.ares.core.serializer.LongJsonDeserializer;
import com.ares.core.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/23
 **/
@Data
@ApiModel(value = "SysMenu对象", description = "系统菜单")
public class SysMenu extends BaseModel implements Serializable {

    private static final long serialVersionUID = -7528858814504220374L;
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;
    @ApiModelProperty("菜单描述")
    private String description;

    @ApiModelProperty(value = "菜单地址", required = true)
    @NotEmpty(message = "url不能为空")
    private String url;
    @ApiModelProperty("菜单路由")
    private String path;
    @ApiModelProperty("")
    private Integer isBlank;

    @ApiModelProperty(value = "父菜单", required = true)
    @NotEmpty(message = "父菜单不能为空")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long pId;
    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单权限", required = true)
    @NotEmpty(message = "权限不能为空")
    private String perms;
    @ApiModelProperty("菜单分类，0：目录1：菜单2：按钮")
    private Integer type;
    @ApiModelProperty("菜单排序")
    private Integer order;
    @ApiModelProperty("是否可见")
    private Integer visible;

    private Integer childCount;

    /**
     * 子菜单
     */
    private List<SysMenu> children = new ArrayList<SysMenu>();
}
