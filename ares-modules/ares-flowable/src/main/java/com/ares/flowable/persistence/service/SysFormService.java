package com.ares.flowable.persistence.service;

import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.flowable.persistence.dao.ISysFormDao;
import com.ares.flowable.persistence.model.SysForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysFormService implements BaseService<SysForm> {

    private ISysFormDao sysFormDao;

    @Autowired
    public SysFormService(ISysFormDao sysFormDao) {
        this.sysFormDao = sysFormDao;
    }

    public PageInfo<SysForm> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysForm> lists = sysFormDao.list(map);
        PageInfo<SysForm> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysForm obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysFormDao.insert(obj);
    }

    @Override
    public void update(SysForm obj) {
        obj.setModifyTime(new Date());
        sysFormDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysFormDao.deleteByIds(ids);
    }

    @Override
    public SysForm getById(String id) {
        return sysFormDao.getById(id);
    }

    public List<SysForm> list(SysForm obj) {
        List<SysForm> lists = sysFormDao.selectList(obj);
        return lists;
    }

}
