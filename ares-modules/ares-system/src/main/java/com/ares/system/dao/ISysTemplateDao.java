package com.ares.system.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysTemplateDao extends IBaseDao<SysTemplate> {
    SysTemplate getByName(@Param("name") String name);
}
