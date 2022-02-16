package com.ares.core.model.system;


import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysProperties对象", description = "系统参数")
public class SysProperties extends BaseModel {
    private static final long serialVersionUID = -8344592553043367264L;
    @ApiModelProperty("参数别名")
    private String alias;
    @ApiModelProperty("参数描述")
    private String description;
    @ApiModelProperty("参数分组")
    private String group;
    @ApiModelProperty("参数名称")
    private String name;
    @ApiModelProperty("参数值")
    private String value;
}
