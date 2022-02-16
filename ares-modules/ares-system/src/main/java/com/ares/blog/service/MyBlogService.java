package com.ares.blog.service;

import com.ares.core.utils.JsonUtils;
import com.ares.system.model.Articles;
import com.ares.system.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2021/04/19
 * @see: com.ares.blog.persistence.service.MyBlogService.java
 **/
@Service
public class MyBlogService {
    private ArticlesService articlesService;

    @Autowired
    public MyBlogService(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    public String getUpdateInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            ClassPathResource resource = new ClassPathResource("/");
            String path = resource.getURL().getPath();
            path = path.replace("target/classes/", "src/main/resources/CHANGELOG.md");
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String result = null;
            while ((result = bufferedReader.readLine()) != null) {
                sb.append(result).append(" \n");
            }

            bufferedReader.close();
            reader.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean saveUpdateInfo(String content) {
        try {
            ClassPathResource resource = new ClassPathResource("/");
            String path = resource.getURL().getPath();
            path = path.replace("target/classes/", "src/main/resources/CHANGELOG.md");
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
            writer.write(content);
            writer.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getUpdateInfo1() {
        Articles articles = new Articles();
        articles.setType("1");
        articles.setStatus("1");
        List<Articles> list = articlesService.list(articles);
        return JsonUtils.toJson(list);
    }
}
