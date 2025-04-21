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

package com.ares.generator.persistence.service;


import cn.hutool.core.lang.Assert;
import com.ares.core.config.gen.GeneratorConfig;
import com.ares.generator.persistence.dao.IGeneratorDao;
import com.ares.generator.persistence.model.EntityDataModel;
import com.ares.generator.persistence.model.GenProperties;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/04/28
 **/
@Slf4j
@Service
public class GeneratorService {

    private IGeneratorDao generatorDao;
    private AutoGeneratorService autoGeneratorService;
    private GeneratorConfig config;
    private GenPropertiesService genPropertiesService;

    @Autowired
    public GeneratorService(IGeneratorDao generatorDao,
                            AutoGeneratorService autoGeneratorService,
                            GeneratorConfig config,
                            GenPropertiesService genPropertiesService) {
        this.generatorDao = generatorDao;
        this.autoGeneratorService = autoGeneratorService;
        this.config = config;
        this.genPropertiesService = genPropertiesService;
    }

    private void init() {
        GenProperties properties = genPropertiesService.getByUser();
        if (null != properties) {
            config.setBasePackage(properties.getBasePackage());
            config.setEntityPackage(properties.getEntityPackage());
            config.setDaoPackage(properties.getDaoPackage());
            config.setServicePackage(properties.getServicePackage());
            config.setControllerPackage(properties.getControllerPackage());
            config.setAuthor(properties.getAuthor());
            config.setGeneratorLevel(properties.getGenLevel());
            config.setTablePrefix(properties.getTablePrefix());
        }
    }

    public List<Map<String, Object>> tables(Map<String, Object> map) {
        List<Map<String, Object>> tables = generatorDao.getTables(map);
        return tables;
    }

    public PageInfo<Map<String, Object>> columns(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> columnList = generatorDao.getColumnsByTable(map);
        PageInfo<Map<String, Object>> columnPageInfo = new PageInfo<>(columnList);
        return columnPageInfo;
    }

    public List<Map<String, Object>> selectTableColumnListByTableName(String tableName) {
        return generatorDao.selectTableColumnListByTableName(tableName);
    }

    public LinkedHashMap<String, Object> preview(String tableName) {
        Assert.notNull(tableName);
        init();
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        String tablePrefix = config.getTablePrefix();

        Connection con = autoGeneratorService.getConn();

        try {
            ByteArrayOutputStream entity = null, entityQuery = null, entityMapper = null, entityDao = null, entityService = null, entityApiController = null, view = null, viewjs = null;
            EntityDataModel entityModel = autoGeneratorService.getEntityModel(con, tableName, tablePrefix);
            switch (config.getGeneratorLevel()) {
                case 1:
                    entity = generateCode(entityModel, "Entity.ftl", "", ".java");
                    entityQuery = generateCode(entityModel, "EntityQuery.ftl", "", "Query.java");
                    entityMapper = generateCode(entityModel, "EntityMapper.ftl", "", "Mapper.xml");
                    entityDao = generateCode(entityModel, "EntityDao.ftl", "I", "Dao.java");
                    break;
                case 2:
                    entity = generateCode(entityModel, "Entity.ftl", "", ".java");
                    entityQuery = generateCode(entityModel, "EntityQuery.ftl", "", "Query.java");
                    entityMapper = generateCode(entityModel, "EntityMapper.ftl", "", "Mapper.xml");
                    entityDao = generateCode(entityModel, "EntityDao.ftl", "I", "Dao.java");
                    entityService = generateCode(entityModel, "EntityService.ftl", "", "Service.java");
                    break;
                case 3:
                    entity = generateCode(entityModel, "Entity.ftl", "", ".java");
                    entityQuery = generateCode(entityModel, "EntityQuery.ftl", "", "Query.java");
                    entityDao = generateCode(entityModel, "EntityDao.ftl", "I", "Dao.java");
                    entityService = generateCode(entityModel, "EntityService.ftl", "", "Service.java");
                    entityMapper = generateCode(entityModel, "EntityMapper.ftl", "", "Mapper.xml");
                    entityApiController = generateCode(entityModel, "EntityApiController.ftl", "", "ApiController.java");
                    view = generateCode(entityModel, "View.ftl", "", ".vue");
                    viewjs = generateCode(entityModel, "View-js.ftl", "", ".js");
                    break;
                default:
                    break;
            }
            result.put("Entity.ftl", entity.toString("utf-8"));
            result.put("EntityQuery.ftl", entityQuery.toString("utf-8"));
            result.put("EntityDao.ftl", entityDao.toString("utf-8"));
            result.put("EntityService.ftl", entityService.toString("utf-8"));
            result.put("EntityMapper.ftl", entityMapper.toString("utf-8"));
            result.put("EntityApiController.ftl", entityApiController.toString("utf-8"));
            result.put("index.ftl", view.toString("utf-8"));
            result.put("js.ftl", viewjs.toString("utf-8"));
        } catch (Exception e) {
            log.error("代码生成出错 {}", e.getMessage());
        }
        return result;
    }


    private ByteArrayOutputStream generateCode(EntityDataModel dataModel, String templateName, String filePrefix, String fileSuffix)
            throws IOException, TemplateException {
        //获取模板路径
        String templatePath = config.getTemplatePath();
        templatePath = templatePath.replace("/", File.separator).replace("\\", File.separator);
        log.info("当前模板路径为：{}", templatePath);

        if (fileSuffix.contains("xml")) {
            dataModel.setInsertValue(autoGeneratorService.handleSQL(dataModel.getColumns(), "insert"));
            dataModel.setUpdateValue(autoGeneratorService.handleSQL(dataModel.getColumns(), "update"));
            dataModel.setStrColumn(autoGeneratorService.handleSQL(dataModel.getColumns(), "columnList"));
        }

        //获取模板对象
        Configuration conf = new Configuration();
        File temp = new File(templatePath);
        conf.setDirectoryForTemplateLoading(temp);
        Template template = conf.getTemplate(templateName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        //填充数据模型
        template.process(dataModel, writer);
        writer.close();
        return outputStream;
    }
}
