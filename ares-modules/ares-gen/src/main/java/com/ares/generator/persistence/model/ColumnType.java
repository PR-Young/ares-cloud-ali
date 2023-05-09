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

/**
 * @description:
 * @author: Young 2020/03/31
 **/
public enum ColumnType {
    STRING("varchar", "String"),
    DATETIME("datetime", "Date"),
    TIMESTAMP("timestamp", "Date"),
    INTEGER("int", "Integer"),
    BIGINT("bigint", "Integer"),
    NUMERIC("numeric", "Long"),
    DECIMAL("decimal", "Long"),
    LONGVARCHAR("longvarchar", "String");

    private String jdbcType;

    private String javaType;

    ColumnType(String jdbcType, String javaType) {
        this.jdbcType = jdbcType;
        this.javaType = javaType;
    }

    public static String getJavaType(String jdbcType) {
        String result = "";
        for (ColumnType type : values()) {
            if (jdbcType.equalsIgnoreCase(type.jdbcType)) {
                result = type.javaType;
            }
        }
        return "".equals(result) ? "String" : result;
    }
}
