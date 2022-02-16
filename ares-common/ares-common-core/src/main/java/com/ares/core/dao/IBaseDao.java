package com.ares.core.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/22
 **/
public interface IBaseDao<T> {
    int insert(T obj);

    int update(T obj);

    int deleteByIds(List<String> ids);

    List<T> list(Map<String, Object> params);

    T getById(@Param("id") String id);

    List<T> selectList(T obj);

}
