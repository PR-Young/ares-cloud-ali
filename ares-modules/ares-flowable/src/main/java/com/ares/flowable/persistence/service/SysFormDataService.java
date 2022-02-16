package com.ares.flowable.persistence.service;


import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.flowable.persistence.dao.ISysFormDataDao;
import com.ares.flowable.persistence.model.SysFormData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysFormDataService implements BaseService<SysFormData> {

    private ISysFormDataDao sysFormDataDao;

    @Autowired
    public SysFormDataService(ISysFormDataDao sysFormDataDao) {
        this.sysFormDataDao = sysFormDataDao;
    }

    public PageInfo<SysFormData> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysFormData> lists = sysFormDataDao.list(map);
        PageInfo<SysFormData> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysFormData obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysFormDataDao.insert(obj);
    }

    @Override
    public void update(SysFormData obj) {
        obj.setModifyTime(new Date());
        sysFormDataDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysFormDataDao.deleteByIds(ids);
    }

    @Override
    public SysFormData getById(String id) {
        return sysFormDataDao.getById(id);
    }

    public List<SysFormData> list(SysFormData obj) {
        List<SysFormData> lists = sysFormDataDao.selectList(obj);
        return lists;
    }

    public SysFormData getFormDataByProInstId(String proInstId) {
        return sysFormDataDao.getFormDataByProInstId(proInstId);
    }

}
