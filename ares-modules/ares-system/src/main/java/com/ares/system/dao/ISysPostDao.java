package com.ares.system.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysPost;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysPostDao extends IBaseDao<SysPost> {
    SysPost getByName(String postName);
}
