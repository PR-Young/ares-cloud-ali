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

package com.ares.system.service;

import com.ares.core.model.system.SysProperties;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysPropertiesDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysPropertiesService implements BaseService<SysProperties> {

    private ISysPropertiesDao sysPropertiesDao;

    @Autowired
    public SysPropertiesService(ISysPropertiesDao sysPropertiesDao) {
        this.sysPropertiesDao = sysPropertiesDao;
    }

    public PageInfo<SysProperties> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysProperties> lists = sysPropertiesDao.list(map);
        PageInfo<SysProperties> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysProperties obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysPropertiesDao.insert(obj);
    }

    @Override
    public void update(SysProperties obj) {
        obj.setModifyTime(new Date());
        sysPropertiesDao.update(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        sysPropertiesDao.deleteByIds(ids);
    }

    @Override
    public SysProperties getById(Long id) {
        return sysPropertiesDao.getById(id);
    }

    public List<SysProperties> list(SysProperties obj) {
        List<SysProperties> lists = sysPropertiesDao.selectList(obj);
        return lists;
    }

    public Map<String, Object> getByGroup(String group) {
        List<SysProperties> properties = sysPropertiesDao.getByGroup(group);
        Map<String, Object> map = properties.stream().collect(Collectors.toMap(SysProperties::getAlias, SysProperties::getValue));
        return map;
    }

    public String getValueByAlias(String alias) {
        return sysPropertiesDao.getValueByAlias(alias);
    }

}
