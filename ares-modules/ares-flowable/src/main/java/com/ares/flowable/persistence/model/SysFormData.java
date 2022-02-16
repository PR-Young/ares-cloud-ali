package com.ares.flowable.persistence.model;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysFormData对象", description = "")
public class SysFormData extends BaseModel {
    @ApiModelProperty("")
    private String proInstId;
    @ApiModelProperty("")
    private String formData;
}
