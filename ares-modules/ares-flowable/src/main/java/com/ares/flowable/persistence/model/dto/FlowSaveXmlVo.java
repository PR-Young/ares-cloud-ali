package com.ares.flowable.persistence.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlowSaveXmlVo implements Serializable {

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程分类
     */
    private String category;

    /**
     * xml 文件
     */
    private String xml;
}
