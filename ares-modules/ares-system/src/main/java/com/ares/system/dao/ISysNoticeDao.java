package com.ares.system.dao;

import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysNoticeDao extends IBaseDao<SysNotice> {

    int noticeNum(@Param("userId") String userId);

    List<SysNotice> getNotices();

}
