package com.ares.flowable.persistence.model;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysForm对象", description = "")
public class SysForm extends BaseModel {
    @ApiModelProperty("")
    private String formName;
    @ApiModelProperty("")
    private String formContent;
    @ApiModelProperty("")
    private String remark;
}
