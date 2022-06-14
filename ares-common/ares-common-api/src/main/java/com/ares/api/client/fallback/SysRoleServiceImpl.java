package com.ares.api.client.fallback;

import com.ares.api.client.ISysRoleService;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.fallback.SysRoleServiceImpl.java
 **/
public class SysRoleServiceImpl implements ISysRoleService {

    @Override
    public JsonResult<List<SysRole>> getRoleByUserId(String userId) {
        return null;
    }

    @Override
    public JsonResult<List<String>> getPermsByRoleId(String roleId) {
        return null;
    }

    @Override
    public JsonResult<SysRole> getById(String roleId) {
        return null;
    }

    @Override
    public JsonResult<List<SysRole>> getAll() {
        return null;
    }

    @Override
    public JsonResult<List<SysRole>> selectRoleList(SysRole role) {
        return null;
    }
}
