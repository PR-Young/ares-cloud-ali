package com.ares.datasource.common;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Young
 * @date: 2020/07/10
 * @see: com.ares.core.common.datasource.DataSource.java
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    DataSourceType value() default DataSourceType.MYSQL;
}
