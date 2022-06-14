package com.ares.api.client.fallback;

import com.ares.api.client.ISysUserService;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysUser;
import org.springframework.stereotype.Component;

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
        return null;
    }

    @Override
    public JsonResult<SysUser> getById(String userId) {
        return null;
    }

    @Override
    public JsonResult<List<SysUser>> selectUserList(SysUser sysUser) {
        return null;
    }
}
