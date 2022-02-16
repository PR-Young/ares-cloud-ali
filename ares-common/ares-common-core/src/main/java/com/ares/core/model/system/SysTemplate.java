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
