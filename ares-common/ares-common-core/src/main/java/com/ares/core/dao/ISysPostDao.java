package com.ares.core.dao;


import com.ares.core.model.system.SysPost;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysPostDao extends IBaseDao<SysPost> {
    SysPost getByName(String postName);
}
