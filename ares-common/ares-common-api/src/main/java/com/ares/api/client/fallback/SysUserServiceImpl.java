package com.ares.api.client.fallback;

import com.ares.api.client.ISysUserService;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysUser;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.fallback.SysUserServiceImpl.java
 **/
public class SysUserServiceImpl implements ISysUserService {
    @Override
    public JsonResult<SysUser> getUserByName(String name) {
        return JsonResult.error(null, 1000, "error");
    }

    @Override
    public JsonResult<SysUser> getById(String userId) {
        return JsonResult.error(null, 1000, "error");
    }

    @Override
    public JsonResult<List<SysUser>> selectUserList(SysUser sysUser) {
        return JsonResult.error(null, 1000, "error");
    }
}
