package com.ares.core.service;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/22
 **/
public abstract interface BaseService<T> {


    public void insert(T obj);

    public void update(T obj);

    public void deleteByIds(List<String> ids);

    public T getById(String id);

}
