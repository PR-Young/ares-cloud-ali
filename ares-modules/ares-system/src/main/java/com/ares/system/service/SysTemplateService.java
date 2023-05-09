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

import com.ares.core.model.system.SysTemplate;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysTemplateDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysTemplateService implements BaseService<SysTemplate> {

    private ISysTemplateDao sysTemplateDao;

    @Autowired
    public SysTemplateService(ISysTemplateDao sysTemplateDao) {
        this.sysTemplateDao = sysTemplateDao;
    }

    public PageInfo<SysTemplate> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysTemplate> lists = sysTemplateDao.list(map);
        PageInfo<SysTemplate> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysTemplate obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysTemplateDao.insert(obj);
    }

    @Override
    public void update(SysTemplate obj) {
        obj.setModifyTime(new Date());
        sysTemplateDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysTemplateDao.deleteByIds(ids);
    }

    @Override
    public SysTemplate getById(String id) {
        return sysTemplateDao.getById(id);
    }

    public List<SysTemplate> list(SysTemplate obj) {
        List<SysTemplate> lists = sysTemplateDao.selectList(obj);
        return lists;
    }


}
