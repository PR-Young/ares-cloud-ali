package com.ares.system.service;

import com.ares.core.model.system.SysDictType;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysDictTypeDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysDictTypeService implements BaseService<SysDictType> {

    private ISysDictTypeDao sysDictTypeDao;

    @Autowired
    public SysDictTypeService(ISysDictTypeDao sysDictTypeDao) {
        this.sysDictTypeDao = sysDictTypeDao;
    }

    public PageInfo<SysDictType> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysDictType> lists = sysDictTypeDao.list(map);
        PageInfo<SysDictType> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysDictType obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysDictTypeDao.insert(obj);
    }

    @Override
    public void update(SysDictType obj) {
        obj.setModifyTime(new Date());
        sysDictTypeDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysDictTypeDao.deleteByIds(ids);
    }

    @Override
    public SysDictType getById(String id) {
        return sysDictTypeDao.getById(id);
    }

    public List<SysDictType> list(SysDictType obj) {
        List<SysDictType> lists = sysDictTypeDao.selectList(obj);
        return lists;
    }


}
