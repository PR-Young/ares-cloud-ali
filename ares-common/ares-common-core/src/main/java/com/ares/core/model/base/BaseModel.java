package com.ares.core.model.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/22
 **/
@Data
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 2513447910190608371L;
    @ApiModelProperty("主键")
    @ExcelProperty(value = "序号", index = 0)
    private String id;
    @ApiModelProperty("创建人")
    @ExcelIgnore
    private String creator;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelIgnore
    private Date createTime;
    @ApiModelProperty("修改人")
    @ExcelIgnore
    private String modifier;
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelIgnore
    private Date modifyTime;

    /** =============================查询参数================================= */
    /**
     * 开始时间
     */
    @JsonIgnore
    @ExcelIgnore
    private String beginTime;
    /**
     * 结束时间
     */
    @JsonIgnore
    @ExcelIgnore
    private String endTime;
    @JsonIgnore
    @ExcelIgnore
    private String sortColumn;
    @JsonIgnore
    @ExcelIgnore
    private String sortAsc;
    @JsonIgnore
    @ExcelIgnore
    private String sort;
    /**
     * 请求参数
     */
    @ExcelIgnore
    private Map<String, Object> params;

}
