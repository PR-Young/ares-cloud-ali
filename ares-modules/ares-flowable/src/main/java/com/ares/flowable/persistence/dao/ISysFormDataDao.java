package com.ares.flowable.persistence.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.flowable.persistence.model.SysFormData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysFormDataDao extends IBaseDao<SysFormData> {
    SysFormData getFormDataByProInstId(@Param("proInstId") String proInstId);
}
