package com.ares.system.service;

import com.ares.core.model.system.SysNoticeRead;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysNoticeReadDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysNoticeReadService implements BaseService<SysNoticeRead> {

    private ISysNoticeReadDao sysNoticeReadDao;

    @Autowired
    public SysNoticeReadService(ISysNoticeReadDao sysNoticeReadDao) {
        this.sysNoticeReadDao = sysNoticeReadDao;
    }

    public PageInfo<SysNoticeRead> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysNoticeRead> lists = sysNoticeReadDao.list(map);
        PageInfo<SysNoticeRead> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysNoticeRead obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysNoticeReadDao.insert(obj);
    }

    @Override
    public void update(SysNoticeRead obj) {
        obj.setModifyTime(new Date());
        sysNoticeReadDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysNoticeReadDao.deleteByIds(ids);
    }

    @Override
    public SysNoticeRead getById(String id) {
        return sysNoticeReadDao.getById(id);
    }

    public List<SysNoticeRead> list(SysNoticeRead obj) {
        List<SysNoticeRead> lists = sysNoticeReadDao.selectList(obj);
        return lists;
    }

}
