package com.ares.core.model.system;

import com.ares.core.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "SysNotice对象", description = "通知公告")
public class SysNotice extends BaseModel {
    private static final long serialVersionUID = -15853869213165737L;
    @ApiModelProperty("到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deadline;
    @ApiModelProperty("内容")
    private String noticeContent;
    @ApiModelProperty("状态：0：正常1：关闭")
    private String noticeStatus;
    @ApiModelProperty("标题")
    private String noticeTitle;
    @ApiModelProperty("类别：1：通知2：公告")
    private String noticeType;
    @ApiModelProperty("备注")
    private String remark;
}
