package com.ares.blog.controller;

import com.ares.system.model.Articles;
import com.ares.system.service.ArticlesService;
import com.ares.blog.service.MyBlogService;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.utils.ServletUtils;
import com.ares.log.common.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2021/04/19
 * @see: com.ares.blog.controller.MyBlogController.java
 **/
@Log(isIgnore = true)
@RestController
@RequestMapping("/blog/*")
public class MyBlogController {
    private MyBlogService blogService;
    private ArticlesService articlesService;

    @Autowired
    public MyBlogController(MyBlogService blogService, ArticlesService articlesService) {
        this.blogService = blogService;
        this.articlesService = articlesService;
    }

    @GetMapping("updateInfo")
    public AjaxResult getUpdateInfo() {
        return AjaxResult.successData(blogService.getUpdateInfo());
    }

    @PostMapping("saveUpdateInfo")
    public AjaxResult saveUpdateInfo() {
        Map<String, Object> parameter = ServletUtils.getParameter();
        String content = String.valueOf(parameter.get("content"));
        return AjaxResult.successData(blogService.saveUpdateInfo(content));
    }


    @GetMapping("updateInfo1")
    public AjaxResult getUpdateInfo1() {
        return AjaxResult.successData(blogService.getUpdateInfo1());
    }

    @GetMapping("getArticles")
    public AjaxResult getArticles() {
        Articles articles = new Articles();
        articles.setStatus("1");
        return AjaxResult.successData(articlesService.list(articles));
    }

    @GetMapping("getArticle/{id}")
    public AjaxResult getArticles(@PathVariable String id) {
        return AjaxResult.successData(articlesService.getById(id));
    }
}
