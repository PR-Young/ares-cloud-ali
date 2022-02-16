package com.ares.core.model.system;


import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/25
 **/
@Data
@ApiModel(value = "SysRole对象", description = "系统角色")
public class SysRole extends BaseModel implements Serializable {

    private static final long serialVersionUID = 3066031349080678671L;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String description;

    /**
     * 菜单组
     */
    private String[] menuIds;

    private String[] userIds;
}
