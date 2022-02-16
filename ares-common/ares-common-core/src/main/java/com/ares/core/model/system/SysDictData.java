package com.ares.core.model.system;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDictData对象", description = "字典数据")
public class SysDictData extends BaseModel {
    private static final long serialVersionUID = 8860820274340442461L;
    @ApiModelProperty("排序")
    private Integer dictSort;
    @ApiModelProperty("字典名称")
    private String dictLabel;
    @ApiModelProperty("字典值")
    private String dictValue;
    @ApiModelProperty("字典类别")
    private String dictType;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("备注")
    private String remark;
}
