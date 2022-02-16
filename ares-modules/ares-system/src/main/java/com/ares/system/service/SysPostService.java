package com.ares.system.service;

import com.ares.core.model.system.SysPost;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.core.dao.ISysPostDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysPostService implements BaseService<SysPost> {

    private ISysPostDao sysPostDao;

    @Autowired
    public SysPostService(ISysPostDao sysPostDao) {
        this.sysPostDao = sysPostDao;
    }

    public PageInfo<SysPost> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysPost> lists = sysPostDao.list(map);
        PageInfo<SysPost> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysPost obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysPostDao.insert(obj);
    }

    @Override
    public void update(SysPost obj) {
        obj.setModifyTime(new Date());
        sysPostDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysPostDao.deleteByIds(ids);
    }

    @Override
    public SysPost getById(String id) {
        return sysPostDao.getById(id);
    }

    public List<SysPost> list(SysPost obj) {
        List<SysPost> lists = sysPostDao.selectList(obj);
        return lists;
    }

    public List<SysPost> getAll() {
        return sysPostDao.list(new HashMap<>());
    }

}
