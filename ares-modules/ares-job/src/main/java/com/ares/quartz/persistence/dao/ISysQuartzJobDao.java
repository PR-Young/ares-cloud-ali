package com.ares.quartz.persistence.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.quartz.persistence.model.SysQuartzJob;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysQuartzJobDao extends IBaseDao<SysQuartzJob> {

    Integer checkUnique(String jobName);

}
