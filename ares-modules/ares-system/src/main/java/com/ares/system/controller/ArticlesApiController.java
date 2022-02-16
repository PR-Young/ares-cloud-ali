package com.ares.system.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.security.common.SecurityUtils;
import com.ares.system.model.Articles;
import com.ares.system.service.ArticlesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/articles/*")
@Api(value = "API", tags = {"管理"})
public class ArticlesApiController extends BaseController {

    private ArticlesService articlesService;

    @Autowired
    public ArticlesApiController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @PreAuthorize("hasAnyAuthority('articles:list')")
    @RequestMapping("list")
    @ApiOperation(value = "列表", response = TableDataInfo.class)
    public TableDataInfo list(Articles articles) {
        startPage();
        List<Articles> articlesList = articlesService.list(articles);
        return getDataTable(articlesList);
    }

    @GetMapping("{articlesId}")
    @ApiOperation(value = "根据Id获取信息", response = Object.class)
    public Object getInfo(@PathVariable String articlesId) {
        return AjaxResult.successData(articlesService.getById(articlesId));
    }

    @PreAuthorize("hasAnyAuthority('articles:edit')")
    @PostMapping("edit")
    @ApiOperation(value = "编辑信息", response = Object.class)
    public Object edit(@Validated @RequestBody Articles articles) throws Exception {
        if (StringUtils.isEmpty(articles.getId())) {
            articles.setCreator(SecurityUtils.getUser().getId());
            articlesService.insert(articles);
        } else {
            articles.setModifier(SecurityUtils.getUser().getId());
            articlesService.update(articles);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("hasAnyAuthority('articles:delete')")
    @DeleteMapping("{articlesIds}")
    @ApiOperation(value = "删除信息", response = Object.class)
    public Object remove(@PathVariable String[] articlesIds) {
        articlesService.deleteByIds(Arrays.asList(articlesIds));
        return AjaxResult.success();
    }
}
