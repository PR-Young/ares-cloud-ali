package com.ares.system.service;

import com.ares.core.model.system.SysLog;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/27
 **/
@Service
public class SysLogService {

    private ISysLogDao sysLogDao;

    @Autowired
    public SysLogService(ISysLogDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    public void insert(SysLog sysLog) {
        sysLog.setId(SnowflakeIdWorker.getUUID());
        sysLog.setCreateTime(new Date());
        sysLogDao.insert(sysLog);
    }

    public List<SysLog> list(SysLog sysLog) {
        List<SysLog> logList = sysLogDao.list(sysLog);
        return logList;
    }
}
