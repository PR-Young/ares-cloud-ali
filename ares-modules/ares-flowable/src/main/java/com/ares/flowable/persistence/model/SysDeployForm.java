package com.ares.flowable.persistence.model;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDeployForm对象", description = "")
public class SysDeployForm extends BaseModel {
    @ApiModelProperty("")
    private String formId;
    @ApiModelProperty("")
    private String deployId;
}
