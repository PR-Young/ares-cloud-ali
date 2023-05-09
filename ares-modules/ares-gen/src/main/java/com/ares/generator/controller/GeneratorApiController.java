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

package com.ares.generator.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.utils.DateUtils;
import com.ares.core.utils.ServletUtils;
import com.ares.generator.persistence.service.AutoGeneratorService;
import com.ares.generator.persistence.service.GeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/05/12
 **/
@RestController
@RequestMapping("/tool/gen/*")
@Api(value = "代码生成API", tags = {"代码生成"})
public class GeneratorApiController extends BaseController {

    private GeneratorService generatorService;
    private AutoGeneratorService autoGeneratorService;

    @Autowired
    public GeneratorApiController(GeneratorService generatorService, AutoGeneratorService autoGeneratorService) {
        this.generatorService = generatorService;
        this.autoGeneratorService = autoGeneratorService;
    }

    @GetMapping("db/list")
    @ApiOperation(value = "表信息", response = TableDataInfo.class)
    public TableDataInfo dataList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", ServletUtils.getParameter("tableName"));
        map.put("tableComment", ServletUtils.getParameter("tableComment"));
        map.put("beginTime", ServletUtils.getParameter("beginTime"));
        map.put("endTime", ServletUtils.getParameter("endTime"));
        startPage();
        List<Map<String, Object>> list = generatorService.tables(map);
        return getDataTable(list);
    }

    @GetMapping(value = "column/{tableName}")
    @ApiOperation(value = "根据表获取字段", response = TableDataInfo.class)
    public TableDataInfo columnList(@PathVariable("tableName") String tableName) {
        TableDataInfo dataInfo = new TableDataInfo();
        List<Map<String, Object>> list = generatorService.selectTableColumnListByTableName(tableName);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    @GetMapping("genCode/{tableName}")
    @ApiOperation(value = "生成代码", response = TableDataInfo.class)
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = autoGeneratorService.generator(tableName);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        String date = DateUtils.dateTimeNow();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code_" + date + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
