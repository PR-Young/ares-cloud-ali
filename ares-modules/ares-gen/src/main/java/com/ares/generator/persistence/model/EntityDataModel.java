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

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Young 2020/03/31
 **/
@Data
public class EntityDataModel {
    /**
     * base package
     */
    private String entityPackage;

    private String daoPackage;

    private String servicePackage;

    private String controllerPackage;
    /**
     * 文件名后缀
     */
    private String fileSuffix;

    /**
     * 实体名
     */
    private String entityName;

    private String entityName1;

    /**
     * 作者 默认
     */
    private String author = "";

    /**
     * 创建时间
     */
    private String createTime = new Date().toString();

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段集合
     */
    private List<Column> columns;

    private String strColumn;

    private String insertValue;

    private String updateValue;

    private String whereIdSql = "where `Id` = #{id,jdbcType=VARCHAR}";

    private String specialId = "#{id}";

    private String specialSort = "${sort}";
}
