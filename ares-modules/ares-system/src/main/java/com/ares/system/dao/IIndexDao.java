package com.ares.system.dao;

import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/14
 * @see: com.ares.system.persistence.dao IIndexDao.java
 **/
@Repository
public interface IIndexDao {
    int getJobNumber();

    int getLogNumber();
}
