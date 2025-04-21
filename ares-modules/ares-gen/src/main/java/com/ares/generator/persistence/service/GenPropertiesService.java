/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

package com.ares.generator.persistence.service;

import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.generator.persistence.dao.IGenPropertiesDao;
import com.ares.generator.persistence.model.GenProperties;
import com.ares.generator.persistence.model.query.GenPropertiesQuery;
import com.ares.security.common.SecurityUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: ares
 * @date: 2025-04-21 10:19:03
 **/
@Service
public class GenPropertiesService implements BaseService<GenProperties> {

    private IGenPropertiesDao genPropertiesDao;

    @Autowired
    public GenPropertiesService(IGenPropertiesDao genPropertiesDao) {
        this.genPropertiesDao = genPropertiesDao;
    }

    public PageInfo<GenProperties> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<GenProperties> lists = genPropertiesDao.list(map);
        PageInfo<GenProperties> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(GenProperties obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        genPropertiesDao.insert(obj);
    }

    @Override
    public void update(GenProperties obj) {
        obj.setModifyTime(new Date());
        genPropertiesDao.update(obj);
    }


    @Override
    public void deleteByIds(List<Long> ids) {
        genPropertiesDao.deleteByIds(ids);
    }

    @Override
    public GenProperties getById(Long id) {
        return genPropertiesDao.getById(id);
    }

    public List<GenProperties> list(GenPropertiesQuery obj) {
        List<GenProperties> lists = genPropertiesDao.selectList(obj);
        return lists;
    }

    public GenProperties getByUser() {
        GenProperties genProperties = genPropertiesDao.getByUser(SecurityUtils.getUser().getId());
        return genProperties;
    }

}
