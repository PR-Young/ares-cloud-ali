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

package com.ares.system.controller;


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.utils.StringUtils;
import com.ares.security.common.SecurityUtils;
import com.ares.system.model.Articles;
import com.ares.system.model.query.ArticlesQuery;
import com.ares.system.service.ArticlesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/articles/*")
@Tag(name = "ArticlesApiController", description = "管理")
public class ArticlesApiController extends BaseController {

    private ArticlesService articlesService;

    @Autowired
    public ArticlesApiController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @PreAuthorize("hasAnyAuthority('articles:list')")
    @RequestMapping("list")
    @Operation(summary = "列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = TableDataInfo.class)))})
    public TableDataInfo list(ArticlesQuery articles) {
        startPage();
        List<Articles> articlesList = articlesService.list(articles);
        return getDataTable(articlesList);
    }

    @GetMapping("{articlesId}")
    @Operation(summary = "根据Id获取信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object getInfo(@PathVariable Long articlesId) {
        return AjaxResult.successData(articlesService.getById(articlesId));
    }

    @PreAuthorize("hasAnyAuthority('articles:edit')")
    @PostMapping("edit")
    @Operation(summary = "编辑信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
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
    @Operation(summary = "删除信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object remove(@PathVariable Long[] articlesIds) {
        articlesService.deleteByIds(Arrays.asList(articlesIds));
        return AjaxResult.success();
    }
}
