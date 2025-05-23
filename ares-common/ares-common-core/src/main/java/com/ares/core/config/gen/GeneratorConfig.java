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

package com.ares.core.config.gen;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Young
 * @date: 2020/11/11
 * @see: com.ares.config.gen.GeneratorConfig.java
 **/
@Data
@Configuration
@RefreshScope
public class GeneratorConfig {

    /**
     * 数据库驱动
     * 未提供注入时请自行提供驱动
     */
    @Value("${ares.generator.driverClassName}")
    private String driverClassName;
    /**
     * 数据库URL
     */
    @Value("${ares.generator.url}")
    private String url;
    /**
     * 用户名
     */
    @Value("${ares.generator.username}")
    private String username;

    /**
     * 密码
     */
    @Value("${ares.generator.password}")
    private String password;

    /**
     * 数据库名
     */
    @Value("${ares.generator.databaseName}")
    private String databaseName;

    /**
     * 只生成单表，非必须
     */
    @Value("${ares.generator.tableName}")
    private String tableName;

    /**
     * 表前缀，非必须
     */
    @Value("${ares.generator.tablePrefix}")
    private String tablePrefix;

    /**
     * 生成级别 必须
     * 1 生成至dao层
     * 2 生成至service层
     * 3 生成至controller层
     */
    @Value("${ares.generator.generatorLevel}")
    private int generatorLevel;

    /**
     * 基本包名 必须
     */
    @Value("${ares.generator.basePackage}")
    private String basePackage;

    /**
     * entity对象报名 无则使用基本包名
     */
    @Value("${ares.generator.entityPackage}")
    private String entityPackage;
    /**
     * dao接口包名 无则使用基本包名
     */
    @Value("${ares.generator.daoPackage}")
    private String daoPackage;

    /**
     * xml 生成路径 无则使用基本包名
     */
    @Value("${ares.generator.xmlDir}")
    private String xmlDir;

    /**
     * servcie包名 无则使用基本包名
     */
    @Value("${ares.generator.servicePackage}")
    private String servicePackage;

    /**
     * controller包名 无则使用基本包名
     */
    @Value("${ares.generator.controllerPackage}")
    private String controllerPackage;

    @Value("${ares.generator.templatePath}")
    private String templatePath;

    @Value("${ares.generator.projectPtah}")
    private String projectPtah;

    @Value("${ares.generator.author}")
    private String author;
}
