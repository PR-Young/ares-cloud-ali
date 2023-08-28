/*
 *
 *  * !******************************************************************************
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

import com.ares.core.model.query.SysLoginInfoQuery;
import com.ares.core.model.system.SysLoginInfo;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysLoginInfoDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysLoginInfoService implements BaseService<SysLoginInfo> {

    private ISysLoginInfoDao sysLoginInfoDao;

    @Autowired
    public SysLoginInfoService(ISysLoginInfoDao sysLoginInfoDao) {
        this.sysLoginInfoDao = sysLoginInfoDao;
    }

    public Long saveInfo(SysLoginInfo sysLoginInfo) {
        Long id = SnowflakeIdWorker.getUUID();
        sysLoginInfo.setId(id);
        sysLoginInfoDao.insert(sysLoginInfo);
        return id;
    }

    public void remove() {
        sysLoginInfoDao.remove();
    }


    public PageInfo<SysLoginInfo> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysLoginInfo> lists = sysLoginInfoDao.list(map);
        PageInfo<SysLoginInfo> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysLoginInfo obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysLoginInfoDao.insert(obj);
    }

    @Override
    public void update(SysLoginInfo obj) {
        obj.setModifyTime(new Date());
        sysLoginInfoDao.update(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        sysLoginInfoDao.deleteByIds(ids);
    }

    @Override
    public SysLoginInfo getById(Long id) {
        return sysLoginInfoDao.getById(id);
    }

    public List<SysLoginInfo> list(SysLoginInfoQuery obj) {
        List<SysLoginInfo> lists = sysLoginInfoDao.selectList(obj);
        return lists;
    }
}
