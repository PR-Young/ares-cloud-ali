package com.ares.system.model;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Articles对象", description = "")
public class Articles extends BaseModel {
    @ApiModelProperty("")
    private String content;
    @ApiModelProperty("")
    private String name;
    @ApiModelProperty("")
    private String status;
    @ApiModelProperty("")
    private String type;
    @ApiModelProperty("")
    private String title;
}
