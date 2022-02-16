package com.ares.system.dao;

import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysNoticeRead;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysNoticeReadDao extends IBaseDao<SysNoticeRead> {

    int batchInsert(List<SysNoticeRead> noticeReadList);

    List<String> getByUser(String userId);
}
