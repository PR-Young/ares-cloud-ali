package com.ares.system.dao;


import com.ares.core.model.system.SysLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/27
 **/
@Repository
public interface ISysLogDao {
    int insert(SysLog sysLog);

    List<SysLog> list(SysLog sysLog);
}
