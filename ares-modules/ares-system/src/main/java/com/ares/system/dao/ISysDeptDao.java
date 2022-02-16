package com.ares.system.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysDeptDao extends IBaseDao<SysDept> {

    List<SysDept> getAllDept();

    SysDept getByDeptId(@Param("id") String id);
}
