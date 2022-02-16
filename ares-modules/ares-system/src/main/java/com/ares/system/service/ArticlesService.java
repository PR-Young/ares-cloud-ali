package com.ares.system.service;

import com.ares.system.dao.IArticlesDao;
import com.ares.system.model.Articles;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
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
    public void deleteByIds(List<String> ids) {
        articlesDao.deleteByIds(ids);
    }

    @Override
    public Articles getById(String id) {
        return articlesDao.getById(id);
    }

    public List<Articles> list(Articles obj) {
        List<Articles> lists = articlesDao.selectList(obj);
        return lists;
    }

}
