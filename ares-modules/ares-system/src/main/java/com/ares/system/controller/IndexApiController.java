package com.ares.system.controller;


import com.ares.core.model.base.AjaxResult;
import com.ares.core.utils.ServletUtils;
import com.ares.message.persistence.model.AresDocument;
import com.ares.message.persistence.service.ElasticsearchService;
import com.ares.system.service.IndexService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description: 主页
 * @author: Young
 * @date: 2020/09/14
 * @see: com.ares.system.controller IndexApiController.java
 **/
@Slf4j
@RestController
@RequestMapping("/index/*")
@Api(value = "index", tags = {"index"})
public class IndexApiController {
    private IndexService indexService;
    private ElasticsearchService elasticsearchService;

    @Autowired
    public IndexApiController(IndexService indexService, ElasticsearchService elasticsearchService) {
        this.indexService = indexService;
        this.elasticsearchService = elasticsearchService;
    }

    @GetMapping("panelGroup")
    public Object getPanelGroup() {
        return AjaxResult.successData(indexService.getPanelGroup());
    }

    @GetMapping("lineChartData")
    public Object getLineChartData() {
        return AjaxResult.successData(indexService.getLineChartData());
    }

    @GetMapping("lineChart")
    public Object getLineChart() {
        return AjaxResult.successData(indexService.getLineChart());
    }

    @RequestMapping("query")
    public Object query() {
        Map<String, Object> parameter = ServletUtils.getParameter();
        String searchValue = String.valueOf(parameter.get("searchValue"));
        Iterable<AresDocument> aresDocuments = elasticsearchService.query(searchValue);
        return AjaxResult.successData(aresDocuments);
    }
}
