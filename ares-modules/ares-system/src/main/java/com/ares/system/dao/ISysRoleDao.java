package com.ares.system.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/25
 **/
@Repository
public interface ISysRoleDao extends IBaseDao<SysRole> {
    List<SysRole> getRoleByUserId(String userId);

    List<String> getPermsByRoleId(String roleId);

    int insertRoleUser(Map<String, Object> map);

    int insertPermission(Map<String, Object> map);

    int deleteRoleUser(String roleId);

    int deletePermission(String roleId);

    int deleteRoleByUser(String userId);

    int checkRoleName(String roleName);

    SysRole getRoleByName(String roleName);
}
