package com.ares.generator.persistence.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/03/31
 **/
@Data
public class Column implements Serializable {
    /**
     * 属性注解
     */
    private String annotation;

    /**
     * 属性名
     */
    private String name;

    /**
     * 属性类型
     */
    private String type;

    /**
     * 属性注释
     */
    private String comment;

    private String columnName;

    private String jdbcType;
}
