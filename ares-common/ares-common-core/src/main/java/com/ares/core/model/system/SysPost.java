package com.ares.core.model.system;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysPost对象", description = "岗位对象")
public class SysPost extends BaseModel {
    private static final long serialVersionUID = -5311455714157532240L;
    @ApiModelProperty("岗位编号")
    private String postCode;
    @ApiModelProperty("岗位名称")
    private String postName;
}
