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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ares.core.config.gen.GeneratorConfig;
import com.ares.generator.persistence.model.Column;
import com.ares.generator.persistence.model.ColumnType;
import com.ares.generator.persistence.model.DataType;
import com.ares.generator.persistence.model.EntityDataModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description:
 * @author: Young
 * @date: 2020/11/11
 * @see: com.ares.generator.persistence.service AutoGeneratorService.java
 **/
@Slf4j
@Service
public class AutoGeneratorService {

    private GeneratorConfig config;

    @Autowired
    public AutoGeneratorService(GeneratorConfig config) {
        this.config = config;
    }

    public byte[] generator(String tableName) {
        return generator(config.getDriverClassName(),
                config.getUrl(),
                config.getUsername(),
                config.getPassword(),
                tableName,
                config.getDatabaseName(),
                config.getTablePrefix());
    }

    private Connection getConn(String driver, String url, String user, String pwd) {
        Connection con = null;
        //注册驱动
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            log.error("获取数据连接失败，{}", e.getMessage());
        }
        return con;
    }

    public byte[] generator(String driver, String url, String user, String pwd, String tableName, String databaseName,
                            String tablePrefix) {
        Connection con = getConn(driver, url, user, pwd);
        //查询dbName所有表
        String sql = "select table_name from information_schema.tables where table_schema='" + databaseName + "'";
        //获取当前项目路径
        String path = AutoGeneratorService.class.getResource("/").getPath();
        path = StrUtil.sub(path, 1, path.indexOf("/target"));
        log.info("当前项目路径为：{}", path);
        //获取模板路径
        String templatePath = System.getProperty("user.dir") + "/ares-generator/src/main/resources/templates";
        log.info("当前模板路径为：{}", templatePath);
        boolean onlySingleTable = StrUtil.isNotBlank(tableName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        String entityDir = autoCodePath();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!onlySingleTable) {
                    tableName = rs.getString(1);
                }

                //根据实体包名创建目录
                if (!FileUtil.exist(entityDir)) {
                    FileUtil.mkdir(entityDir);
                    log.info("创建目录：{} 成功！ ", entityDir);
                }

                EntityDataModel entityModel = getEntityModel(con,
                        tableName,
                        config.getBasePackage(),
                        config.getDaoPackage(),
                        config.getServicePackage(),
                        config.getControllerPackage(),
                        tablePrefix);
                switch (config.getGeneratorLevel()) {
                    case 1:
                        generateCode(entityModel, templatePath, "Entity.ftl", entityDir, "", ".java", zip);
                        generateCode(entityModel, templatePath, "EntityMapper.ftl", entityDir, "", "Mapper.xml", zip);
                        generateCode(entityModel, templatePath, "EntityDao.ftl", entityDir, "I", "Dao.java", zip);
                        break;
                    case 2:
                        generateCode(entityModel, templatePath, "Entity.ftl", entityDir, "", ".java", zip);
                        generateCode(entityModel, templatePath, "EntityMapper.ftl", entityDir, "", "Mapper.xml", zip);
                        generateCode(entityModel, templatePath, "EntityDao.ftl", entityDir, "I", "Dao.java", zip);
                        generateCode(entityModel, templatePath, "EntityService.ftl", entityDir, "", "Service.java", zip);
                        break;
                    case 3:
                        generateCode(entityModel, templatePath, "Entity.ftl", entityDir, "", ".java", zip);
                        generateCode(entityModel, templatePath, "EntityDao.ftl", entityDir, "I", "Dao.java", zip);
                        generateCode(entityModel, templatePath, "EntityService.ftl", entityDir, "", "Service.java", zip);
                        generateCode(entityModel, templatePath, "EntityMapper.ftl", entityDir, "", "Mapper.xml", zip);
                        generateCode(entityModel, templatePath, "EntityApiController.ftl", entityDir, "", "ApiController.java", zip);
                        generateCode(entityModel, templatePath, "View.ftl", entityDir, "", ".vue", zip);
                        generateCode(entityModel, templatePath, "View-js.ftl", entityDir, "", ".js", zip);
                        break;
                    default:
                        break;
                }
                if (onlySingleTable) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("代码生成出错 {}", e.getMessage());
        } finally {
            try {
                zip.close();
                deleteFiles(entityDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    private void generateCode(EntityDataModel dataModel, String templatePath, String templateName, String outDir, String filePrefix, String fileSuffix, ZipOutputStream zip)
            throws IOException, TemplateException {
        if (fileSuffix.contains("xml")) {
            dataModel.setInsertValue(handleSQL(dataModel.getColumns(), "insert"));
            dataModel.setUpdateValue(handleSQL(dataModel.getColumns(), "update"));
            dataModel.setStrColumn(handleSQL(dataModel.getColumns(), "columnList"));
        }

        String file = outDir + File.separator + filePrefix + dataModel.getEntityName() + fileSuffix;
        if (FileUtil.exist(file)) {
            boolean delete = FileUtil.del(file);
            log.info("文件：{} 已存在，删除:{}", file, delete);
            if (!delete) {
                return;
            }
        }

        //获取模板对象
        Configuration conf = new Configuration();
        File temp = new File(templatePath);
        conf.setDirectoryForTemplateLoading(temp);
        Template template = conf.getTemplate(templateName);
        Writer writer = new FileWriter(file);
        //填充数据模型
        template.process(dataModel, writer);
        writer.close();
        log.info("代码生成成功，文件位置：{}", file);

        FileInputStream inputStream = new FileInputStream(new File(file));
        zip.putNextEntry(new ZipEntry(new File(file).getName()));
        byte[] buffer = new byte[inputStream.available()];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            zip.write(buffer, 0, len);
        }
        zip.flush();
        zip.closeEntry();
        inputStream.close();
    }

    private String handleSQL(List<Column> columns, String flag) {
        StringBuffer sb = new StringBuffer();
        switch (flag) {
            case "columnList":
                for (Column column : columns) {
                    sb.append("`").append(column.getColumnName()).append("`,");
                }
                break;
            case "insert":
                sb.append("#{id,jdbcType=BIGINT},\n");
                for (Column column : columns) {
                    sb.append("#{").append(column.getName()).append(",jdbcType=").append(column.getJdbcType()).append("},\n");
                }
                sb.append("#{creator,jdbcType=BIGINT},\n")
                        .append("#{createTime,jdbcType=TIMESTAMP},\n")
                        .append("#{modifier,jdbcType=BIGINT},\n")
                        .append("#{modifyTime,jdbcType=TIMESTAMP}");
                break;
            case "update":
                for (Column column : columns) {
                    sb.append("<if test=\"").append(column.getName()).append("!= null\">\n")
                            .append("`").append(column.getColumnName()).append("`=#{").append(column.getName()).append(",jdbcType=")
                            .append(column.getJdbcType()).append("},").append("\n")
                            .append("</if>\n");
                }
                sb.append("<if test=\"creator != null\">\n")
                        .append("`Creator` = #{creator,jdbcType=TIMESTAMP},\n")
                        .append("</if>\n")
                        .append("<if test=\"createTime != null\">\n")
                        .append("`CreateTime` = #{createTime,jdbcType=TIMESTAMP},\n")
                        .append("</if>\n")
                        .append("<if test=\"modifier != null\">\n")
                        .append("`Modifier` = #{modifier,jdbcType=TIMESTAMP},\n")
                        .append("</if>\n")
                        .append("<if test=\"modifyTime != null\">\n")
                        .append("`ModifyTime` = #{modifyTime,jdbcType=TIMESTAMP},\n")
                        .append("</if>");
                break;
            default:
                break;
        }
        return sb.toString();
    }

    private EntityDataModel getEntityModel(Connection con, String tableName, String basePackage, String daoPackage, String servicePackage,
                                           String controllerPackage, String tablePrefix)
            throws Exception {
        //查询表属性,格式化生成实体所需属性
        String sql = "SELECT table_name, column_name, column_comment, column_type, column_key, column_default "
                + "FROM INFORMATION_SCHEMA. COLUMNS " + "WHERE table_name = '" + tableName + "' " + "AND table_schema = '" + config.getDatabaseName() + "'";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Column> columns = new ArrayList<>();
        while (rs.next()) {
            Column col = new Column();
            String name = rs.getString("column_name");
            String type = rs.getString("column_type");
            String comment = rs.getString("column_comment");
            String annotation = null;
            if ("Id".equalsIgnoreCase(name)
                    || "CreateTime".equalsIgnoreCase(name)
                    || "ModifyTime".equalsIgnoreCase(name)
                    || "Creator".equalsIgnoreCase(name)
                    || "Modifier".equalsIgnoreCase(name)) {
                continue;
            }
            type = ColumnType.getJavaType(type);
            col.setName(StrUtil.lowerFirst(StrUtil.toCamelCase(name)));
            col.setColumnName(name);
            col.setType(type);
            col.setAnnotation(annotation);
            col.setComment(comment);
            col.setJdbcType(DataType.getJdbcType(type));
            columns.add(col);
        }
        EntityDataModel dataModel = new EntityDataModel();
        dataModel.setEntityPackage(basePackage);
        dataModel.setDaoPackage(daoPackage);
        dataModel.setServicePackage(servicePackage);
        dataModel.setControllerPackage(controllerPackage);
        dataModel.setCreateTime(new Date().toString());
        if (StrUtil.isNotBlank(tablePrefix)) {
            dataModel.setEntityName(StrUtil.upperFirst(StrUtil.toCamelCase(StrUtil.removePrefix(tableName, tablePrefix))));
            dataModel.setEntityName1(StrUtil.lowerFirst(StrUtil.toCamelCase(StrUtil.removePrefix(tableName, tablePrefix))));
        } else {
            dataModel.setEntityName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
            dataModel.setEntityName1(StrUtil.lowerFirst(StrUtil.toCamelCase(tableName)));
        }
        dataModel.setTableName(tableName);
        dataModel.setColumns(columns);
        return dataModel;
    }

    private void deleteFiles(String url) {
        File file = new File(url);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                FileUtil.del(f);
            }
        }
    }

    public String autoCodePath() {
        //获取当前项目路径
        String path = AutoGeneratorService.class.getResource("/").getPath();
        if (path.indexOf("/ares-system-new") > 0) {
            path = StrUtil.sub(path, 0, path.indexOf("/ares-system-new"));
        } else {
            path = StrUtil.sub(path, 0, path.indexOf("/ares-system"));
        }
        return path + File.separator + "autocode";
    }
}
