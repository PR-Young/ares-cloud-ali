package com.ares.api.client;

import com.ares.api.client.fallback.SysRoleServiceImpl;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.api.client.ISysRoleService.java
 **/
@FeignClient(name = "ares-system", fallback = SysRoleServiceImpl.class)
public interface ISysRoleService {
    /**
     * 根据userId获取角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/system/role/getRoleByUserId/{userId}")
    JsonResult<List<SysRole>> getRoleByUserId(@PathVariable("userId") String userId);

    /**
     * 根据id获取角色权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/system/role/getPermsByRoleId/{roleId}")
    JsonResult<List<String>> getPermsByRoleId(@PathVariable("roleId") String roleId);

    /**
     * 根据id获取角色
     *
     * @param roleId
     * @return
     */
    @GetMapping("/system/role/getRoleById/{roleId}")
    JsonResult<SysRole> getById(@PathVariable("roleId") String roleId);

    /**
     * 获取所有角色
     *
     * @return
     */
    @GetMapping("/system/role/getAllRole")
    JsonResult<List<SysRole>> getAll();

    /**
     * 获取角色
     *
     * @param role
     * @return
     */
    @RequestMapping("/system/role/selectRoleList")
    JsonResult<List<SysRole>> selectRoleList(SysRole role);
}
