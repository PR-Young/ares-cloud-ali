package com.ares.core.model.system;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDictType对象", description = "字典类别")
public class SysDictType extends BaseModel {
    private static final long serialVersionUID = -8641334815971550904L;
    @ApiModelProperty("字典名称")
    private String dictName;
    @ApiModelProperty("字典类别")
    private String dictType;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("备注")
    private String remark;
}
