/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

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
