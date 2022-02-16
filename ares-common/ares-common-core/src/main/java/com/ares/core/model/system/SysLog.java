package com.ares.core.model.system;


import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/27
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLog对象", description = "系统日志")
public class SysLog extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2925066346168762533L;
    @ApiModelProperty("主机IP")
    private String hostIp;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("url地址")
    private String url;
    @ApiModelProperty("操作参数")
    private String operParams;
    @ApiModelProperty("备注")
    private String notes;
    @ApiModelProperty("请求方式")
    private String requestMethod;
}
