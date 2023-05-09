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
public enum DataType {

    VARCHAR("varchar", "VARCHAR"),
    TIMESTAMP("datetime", "TIMESTAMP"),
    INTEGER("int", "INTEGER"),
    BIGINT("bigint", "BIGINT"),
    FLOAT("float", "FLOAT"),
    DOUBLE("double", "DOUBLE"),
    NUMERIC("numeric", "NUMERIC"),
    DECIMAL("decimal", "DECIMAL"),
    LONGVARCHAR("longvarchar", "LONGVARCHAR");

    private String type;

    private String jdbcType;

    DataType(String type, String jdbcType) {
        this.jdbcType = jdbcType;
        this.type = type;
    }

    public static String getJdbcType(String type) {
        String result = "";
        for (DataType jdbcType : values()) {
            if (type.equalsIgnoreCase(jdbcType.type)) {
                result = jdbcType.jdbcType;
            }
        }
        return "".equals(result) ? "VARCHAR" : result;
    }
}
