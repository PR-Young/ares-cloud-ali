package com.ares.system.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import com.ares.core.model.system.SysPost;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.core.service.BaseService;
import com.ares.core.utils.MD5Util;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.core.utils.SpringUtils;
import com.ares.core.utils.StringUtils;

import com.ares.system.dao.ISysPostDao;
import com.ares.system.dao.ISysRoleDao;
import com.ares.system.dao.ISysUserDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Young 2020/01/22
 **/
@Service
public class SysUserService implements BaseService<SysUser> {

    private ISysUserDao sysUserDao;
    private ISysRoleDao roleDao;

    @Autowired
    public SysUserService(ISysUserDao sysUserDao, ISysRoleDao roleDao) {
        this.sysUserDao = sysUserDao;
        this.roleDao = roleDao;
    }

    @Override
    public void insert(SysUser sysUser) {
        sysUser.setId(SnowflakeIdWorker.getUUID());
        sysUser.setPassword(MD5Util.encode("123456"));
        sysUser.setCreateTime(new Date());
        sysUserDao.insert(sysUser);
    }

    public PageInfo<SysUser> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysUser> userList = sysUserDao.list(map);
        PageInfo<SysUser> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }

    public List<SysUser> assignAllUser(String roleId) {
        List<SysUser> userList = sysUserDao.allUser(roleId);
        return userList;
    }

    public List<SysUser> getUserByRole(String roleId) {
        List<SysUser> userList = sysUserDao.getUserByRole(roleId);
        return userList;
    }

    @Override
    public void update(SysUser obj) {
        obj.setModifyTime(new Date());
        sysUserDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysUserDao.deleteByIds(ids);
    }

    @Override
    public SysUser getById(String id) {
        return sysUserDao.getById(id);
    }

    public int checkAccount(String account) {
        Integer num = sysUserDao.checkAccount(account);
        return num == null ? 0 : num;
    }

    public int resetPassword(String id) {
        return sysUserDao.resetPassword(MD5Util.encode("123456"), id);
    }

    public List<SysUser> selectUserList(SysUser user) {
        return sysUserDao.selectList(user);
    }

    public String insertUser(SysUser sysUser) {
        String id = SnowflakeIdWorker.getUUID();
        sysUser.setId(id);
        sysUser.setPassword(MD5Util.encode("123456"));
        sysUser.setCreateTime(new Date());
        sysUserDao.insert(sysUser);
        return id;
    }

    public String selectUserRoleGroup(String userId) {
        List<SysRole> roleList = roleDao.getRoleByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : roleList) {
            idsStr.append(role.getDescription()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    public int updatePassword(SysUser user, String passWord) {
        user.setPassword(MD5Util.encode(passWord));
        return sysUserDao.update(user);
    }

    public SysUser getUserByName(String userName) {
        return sysUserDao.getUserByName(userName);
    }

    /**
     * 用户信息导入处理类
     */
    public class UserDataListener extends AnalysisEventListener<SysUser> {
        private Logger logger = LoggerFactory.getLogger(UserDataListener.class);
        private static final int BATCH_COUNT = 100;
        private static final String POST_NAME = "员工";
        private static final String ROLE_NAME = "user";
        List<SysUser> userList = new ArrayList<>();
        private boolean needUpdate = true;
        private String deptId;

        public UserDataListener(boolean needUpdate, String deptId) {
            this.needUpdate = needUpdate;
            this.deptId = deptId;
        }

        @Override
        public void invoke(SysUser sysUser, AnalysisContext analysisContext) {
            logger.info("解析到一条数据:{}", JSON.toJSONString(sysUser));
            userList.add(sysUser);
            if (userList.size() >= BATCH_COUNT) {
                saveData();
                userList.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            saveData();
            logger.info("所有数据解析完成！");
        }

        private void saveData() {
            logger.info("{}条数据，开始存储数据库！", userList.size());
            ISysPostDao postDao = SpringUtils.getBean(ISysPostDao.class);
            SysPost post = postDao.getByName(POST_NAME);
            ISysRoleDao roleDao = SpringUtils.getBean(ISysRoleDao.class);
            SysRole role = roleDao.getRoleByName(ROLE_NAME);
            for (SysUser user : userList) {
                if (checkAccount(user.getAccount()) > 0) {
                    if (needUpdate) {
                        SysUser oldUser = sysUserDao.getUserByName(user.getAccount());
                        oldUser.setUserName(user.getUserName());
                        oldUser.setPhoneNumber(user.getPhoneNumber());
                        oldUser.setEmail(user.getEmail());
                        sysUserDao.updateUserByAccount(oldUser);
                    }
                } else {
                    user.setDeptId(this.deptId);
                    user.setPostId(post.getId());
                    String userId = insertUser(user);
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", SnowflakeIdWorker.getUUID());
                    map.put("userId", userId);
                    map.put("roleId", role.getId());
                    roleDao.insertRoleUser(map);
                }
            }
            logger.info("存储数据库成功！");
        }
    }
}
