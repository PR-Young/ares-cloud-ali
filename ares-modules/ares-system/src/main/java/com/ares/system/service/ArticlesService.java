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

package com.ares.system.service;

import com.ares.system.dao.IArticlesDao;
import com.ares.system.model.Articles;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.model.query.ArticlesQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

;

@Service
public class ArticlesService implements BaseService<Articles> {

    private IArticlesDao articlesDao;

    @Autowired
    public ArticlesService(IArticlesDao articlesDao) {
        this.articlesDao = articlesDao;
    }

    public PageInfo<Articles> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<Articles> lists = articlesDao.list(map);
        PageInfo<Articles> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(Articles obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        articlesDao.insert(obj);
    }

    @Override
    public void update(Articles obj) {
        obj.setModifyTime(new Date());
        articlesDao.update(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        articlesDao.deleteByIds(ids);
    }

    @Override
    public Articles getById(Long id) {
        return articlesDao.getById(id);
    }

    public List<Articles> list(ArticlesQuery obj) {
        List<Articles> lists = articlesDao.selectList(obj);
        return lists;
    }

}
