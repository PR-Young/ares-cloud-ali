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

import com.ares.core.model.query.SysNoticeQuery;
import com.ares.core.model.system.SysNotice;
import com.ares.core.model.system.SysNoticeRead;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysNoticeDao;
import com.ares.system.dao.ISysNoticeReadDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysNoticeService implements BaseService<SysNotice> {

    private ISysNoticeDao sysNoticeDao;
    private ISysNoticeReadDao noticeReadDao;

    @Autowired
    public SysNoticeService(ISysNoticeDao sysNoticeDao, ISysNoticeReadDao noticeReadDao) {
        this.sysNoticeDao = sysNoticeDao;
        this.noticeReadDao = noticeReadDao;
    }

    public PageInfo<SysNotice> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysNotice> lists = sysNoticeDao.list(map);
        PageInfo<SysNotice> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysNotice obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysNoticeDao.insert(obj);
    }

    @Override
    public void update(SysNotice obj) {
        obj.setModifyTime(new Date());
        sysNoticeDao.update(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        sysNoticeDao.deleteByIds(ids);
    }

    @Override
    public SysNotice getById(Long id) {
        return sysNoticeDao.getById(id);
    }

    public List<SysNotice> list(SysNoticeQuery obj) {
        List<SysNotice> lists = sysNoticeDao.selectList(obj);
        return lists;
    }

    public int noticeNum(Long userId) {
        return sysNoticeDao.noticeNum(userId);
    }

    public List<SysNotice> getNotices(Long userId) {
        List<SysNotice> noticeList = sysNoticeDao.getNotices();
        List<SysNoticeRead> noticeReads = new ArrayList<>();
        if (null != noticeList && noticeList.size() > 0) {
            List<Long> noticeReadList = noticeReadDao.getByUser(userId);
            for (SysNotice sysNotice : noticeList) {
                if (noticeReadList.contains(sysNotice.getId())) {
                    continue;
                }
                SysNoticeRead sysNoticeRead = new SysNoticeRead();
                sysNoticeRead.setId(SnowflakeIdWorker.getUUID());
                sysNoticeRead.setNoticeId(sysNotice.getId());
                sysNoticeRead.setUserId(userId);
                sysNoticeRead.setCreator(userId);
                sysNoticeRead.setCreateTime(new Date());
                noticeReads.add(sysNoticeRead);
            }
        }
        if (null != noticeReads && noticeReads.size() > 0) {
            noticeReadDao.batchInsert(noticeReads);
        }
        return noticeList;
    }

}
