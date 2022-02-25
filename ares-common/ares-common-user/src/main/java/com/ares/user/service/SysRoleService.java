package com.ares.user.service;

import com.ares.core.model.system.SysRole;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.core.utils.StringUtils;
import com.ares.user.dao.ISysRoleDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Young 2020/01/26
 **/
@Service
public class SysRoleService implements BaseService<SysRole> {

    private ISysRoleDao sysRoleDao;

    @Autowired
    public SysRoleService(ISysRoleDao sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    public PageInfo<SysRole> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysRole> userList = sysRoleDao.list(map);
        PageInfo<SysRole> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }

    @Override
    public void insert(SysRole obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysRoleDao.insert(obj);
    }

    @Override
    public void update(SysRole obj) {
        obj.setModifyTime(new Date());
        sysRoleDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysRoleDao.deleteByIds(ids);
    }

    @Override
    public SysRole getById(String id) {
        return sysRoleDao.getById(id);
    }

    public void saveAssign(String users, String menus, String roleId) {
        if (StringUtils.isNotEmpty(users)) {
            sysRoleDao.deleteRoleUser(roleId);
            String[] userIds = users.split(",");
            for (String id : userIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("userId", id);
                map.put("roleId", roleId);
                sysRoleDao.insertRoleUser(map);
            }
        }
        if (StringUtils.isNotEmpty(menus)) {
            sysRoleDao.deletePermission(roleId);
            String[] menuIds = menus.split(",");
            for (String id : menuIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("menuId", id);
                map.put("roleId", roleId);
                sysRoleDao.insertPermission(map);
            }
        }
    }

    public List<SysRole> getRoleByUserId(String userId) {
        return sysRoleDao.getRoleByUserId(userId);
    }

    public List<String> getPermsByRoleId(String roleId) {
        return sysRoleDao.getPermsByRoleId(roleId);
    }

    public List<String> getRoleIdsByUser(String userId) {
        List<SysRole> roleList = sysRoleDao.getRoleByUserId(userId);
        List<String> roles = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
        return roles;
    }

    public List<SysRole> getAll() {
        return sysRoleDao.list(new HashMap<>());
    }

    @Transactional
    public void saveRoleUser(String[] roleIds, String userId) {
        sysRoleDao.deleteRoleByUser(userId);
        for (String id : roleIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", SnowflakeIdWorker.getUUID());
            map.put("userId", userId);
            map.put("roleId", id);
            sysRoleDao.insertRoleUser(map);
        }
    }

    public List<SysRole> selectRoleList(SysRole role) {
        return sysRoleDao.selectList(role);
    }

    public boolean checkRoleName(String roleName) {
        int count = sysRoleDao.checkRoleName(roleName);
        return count == 0 ? true : false;
    }

    @Transactional
    public String insertRole(SysRole obj) {
        String roleId = SnowflakeIdWorker.getUUID();
        obj.setId(roleId);
        obj.setCreateTime(new Date());
        sysRoleDao.insert(obj);
        if (StringUtils.isNotEmpty(obj.getMenuIds())) {
            sysRoleDao.deletePermission(roleId);
            for (String id : obj.getMenuIds()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("menuId", id);
                map.put("roleId", roleId);
                sysRoleDao.insertPermission(map);
            }
        }
        return roleId;
    }

    @Transactional
    public void updateRole(SysRole obj) {
        obj.setModifyTime(new Date());
        sysRoleDao.update(obj);
        if (StringUtils.isNotEmpty(obj.getMenuIds())) {
            sysRoleDao.deletePermission(obj.getId());
            for (String id : obj.getMenuIds()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("menuId", id);
                map.put("roleId", obj.getId());
                sysRoleDao.insertPermission(map);
            }
        }
    }

    @Transactional
    public void authDataScope(SysRole role) {
        role.setModifyTime(new Date());
        // 修改角色信息
        sysRoleDao.update(role);
        if (StringUtils.isNotEmpty(role.getUserIds())) {
            sysRoleDao.deleteRoleUser(role.getId());
            for (String id : role.getUserIds()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("userId", id);
                map.put("roleId", role.getId());
                sysRoleDao.insertRoleUser(map);
            }
        }
    }
}
