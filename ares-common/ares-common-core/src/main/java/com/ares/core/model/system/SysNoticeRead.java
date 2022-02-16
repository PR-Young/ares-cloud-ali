package com.ares.core.model.system;

import com.ares.core.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysNoticeRead对象", description = "")
public class SysNoticeRead extends BaseModel {
    private static final long serialVersionUID = -6797150919500326591L;
    @ApiModelProperty("")
    private String noticeId;
    @ApiModelProperty("")
    private String userId;
}
