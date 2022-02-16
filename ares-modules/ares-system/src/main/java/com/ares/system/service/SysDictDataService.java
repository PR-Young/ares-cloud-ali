package com.ares.system.service;

import com.ares.core.model.system.SysDictData;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysDictDataDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysDictDataService implements BaseService<SysDictData> {

    private ISysDictDataDao sysDictDataDao;

    @Autowired
    public SysDictDataService(ISysDictDataDao sysDictDataDao) {
        this.sysDictDataDao = sysDictDataDao;
    }

    public PageInfo<SysDictData> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysDictData> lists = sysDictDataDao.list(map);
        PageInfo<SysDictData> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysDictData obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysDictDataDao.insert(obj);
    }

    @Override
    public void update(SysDictData obj) {
        obj.setModifyTime(new Date());
        sysDictDataDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysDictDataDao.deleteByIds(ids);
    }

    @Override
    public SysDictData getById(String id) {
        return sysDictDataDao.getById(id);
    }

    public List<SysDictData> list(SysDictData obj) {
        List<SysDictData> lists = sysDictDataDao.selectList(obj);
        return lists;
    }

    public List<SysDictData> getDicts(String dictType) {
        return sysDictDataDao.getDicts(dictType);
    }

}
