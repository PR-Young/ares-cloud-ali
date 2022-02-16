package com.ares.quartz.persistence.dao;

import com.ares.core.dao.IBaseDao;
import com.ares.quartz.persistence.model.SysQuartzJobLog;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysQuartzJobLogDao extends IBaseDao<SysQuartzJobLog> {

    int cleanJobLog();

}
