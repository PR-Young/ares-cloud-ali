package com.ares.core.model.system;


import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDept对象", description = "部门对象")
public class SysDept extends BaseModel {
    private static final long serialVersionUID = -2238247302831731612L;
    @ApiModelProperty("部门编号")
    private String code;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("父部门Id")
    private String parentDeptId;

    private String parentDeptName;

    private int childCount;
}
