/*
 *
 *  * ****************************************************************************
 *  * * Copyright (c) 2021 - 9999, ARES
 *  * *
 *  * * Licensed under the Apache License, Version 2.0 (the "License");
 *  * * you may not use this file except in compliance with the License.
 *  * * You may obtain a copy of the License at
 *  * *
 *  * *        http://www.apache.org/licenses/LICENSE-2.0
 *  * *
 *  * * Unless required by applicable law or agreed to in writing, software
 *  * * distributed under the License is distributed on an "AS IS" BASIS,
 *  * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * * See the License for the specific language governing permissions and
 *  * * limitations under the License.
 *  * ***************************************************************************
 *
 */

package com.ares.flow.persistence.service;


import com.ares.api.client.ISysDeptService;
import com.ares.api.client.ISysPostService;
import com.ares.api.client.ISysRoleService;
import com.ares.api.client.ISysUserService;
import com.ares.core.model.system.SysDept;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.core.utils.DateUtils;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.warm.flow.core.utils.CollUtil;
import org.dromara.warm.flow.core.utils.MapUtil;
import org.dromara.warm.flow.core.utils.MathUtil;
import org.dromara.warm.flow.ui.dto.HandlerFunDto;
import org.dromara.warm.flow.ui.dto.HandlerQuery;
import org.dromara.warm.flow.ui.dto.TreeFunDto;
import org.dromara.warm.flow.ui.service.HandlerSelectService;
import org.dromara.warm.flow.ui.vo.HandlerFeedBackVo;
import org.dromara.warm.flow.ui.vo.HandlerSelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Young
 * @date: 2025/5/30
 * @see: com.ares.flow.persistence.service.impl.HandlerSelectServiceImpl.java
 **/
@Service
public class HandlerSelectServiceImpl implements HandlerSelectService {

    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysUserService.class, check = false)
    private ISysUserService sysUserService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysDeptService.class, check = false)
    private ISysDeptService sysDeptService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysPostService.class, check = false)
    private ISysPostService postService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysRoleService.class, check = false)
    private ISysRoleService sysRoleService;

    @Autowired
    public HandlerSelectServiceImpl(ISysUserService sysUserService,
                                    ISysDeptService sysDeptService,
                                    ISysPostService postService,
                                    ISysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysDeptService = sysDeptService;
        this.postService = postService;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public List<String> getHandlerType() {
        return Arrays.asList("用户", "角色", "部门");
    }

    @Override
    public HandlerSelectVo getHandlerSelect(HandlerQuery handlerQuery) {
        if ("角色".equals(handlerQuery.getHandlerType())) {
            return getRole(handlerQuery);
        }

        if ("部门".equals(handlerQuery.getHandlerType())) {
            return getDept(handlerQuery);
        }

        if ("用户".equals(handlerQuery.getHandlerType())) {
            return getUser(handlerQuery);
        }

        return new HandlerSelectVo();
    }

    @Override
    public List<HandlerFeedBackVo> handlerFeedback(List<String> storageIds) {
        List<HandlerFeedBackVo> handlerFeedBackVos = new ArrayList<>();
        if (CollUtil.isNotEmpty(storageIds)) {
            List<Long> roleIdList = new ArrayList<>();
            List<Long> deptIdList = new ArrayList<>();
            List<Long> userIdList = new ArrayList<>();

            // 分别过滤出用户、角色和部门的id，分别用集合存储
            for (String storageId : storageIds) {
                if (storageId.startsWith("role:")) {
                    roleIdList.add(Long.valueOf(storageId.replace("role:", "")));
                } else if (storageId.startsWith("dept:")) {
                    deptIdList.add(Long.valueOf(storageId.replace("dept:", "")));
                } else if (MathUtil.isNumeric(storageId)) {
                    userIdList.add(Long.valueOf(storageId));
                }
            }

            Map<String, String> authMap = new HashMap<>();
            // 查询角色id对应的名称
            if (CollUtil.isNotEmpty(roleIdList)) {
                // 查询角色列表
                for (Long roleId : roleIdList) {
                    SysRole role = sysRoleService.getById(roleId);
                    authMap.put("role:" + role.getId(), role.getRoleName());
                }
            }

            // 查询部门id对应的名称
            if (CollUtil.isNotEmpty(deptIdList)) {
                for (Long deptId : deptIdList) {
                    SysDept dept = sysDeptService.getByDeptId(deptId);
                    authMap.put("dept:" + dept.getId(), dept.getDeptName());
                }
            }

            // 查询用户id对应的名称
            if (CollUtil.isNotEmpty(userIdList)) {
                for (Long userId : userIdList) {
                    SysUser user = sysUserService.getById(userId);
                    authMap.put(String.valueOf(user.getId()), user.getUserName());
                }
            }

            // 遍历storageIds，按照原本的顺序回显名称
            for (String storageId : storageIds) {
                handlerFeedBackVos.add(new HandlerFeedBackVo(storageId, MapUtil.isEmpty(authMap) ? "" : authMap.get(storageId)));
            }
        }

        return handlerFeedBackVos;
    }

    /**
     * 获取角色列表
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    private HandlerSelectVo getRole(HandlerQuery query) {
        // 查询角色列表
        List<SysRole> roleList = sysRoleService.selectRoleList(null);
        long total = new PageInfo<>(roleList).getTotal();

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<SysRole> handlerFunDto = new HandlerFunDto<>(roleList, total)
                // 以下设置获取内置变量的Function
                .setStorageId(role -> "role:" + role.getId()) // 前面拼接role:  是为了防止用户、角色的主键重复
                .setHandlerCode(SysRole::getRoleName) // 权限编码
                .setHandlerName(SysRole::getDescription) // 权限名称
                .setCreateTime(role -> DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, role.getCreateTime()));

        return getHandlerSelectVo(handlerFunDto);
    }

    /**
     * 获取用户列表
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    private HandlerSelectVo getDept(HandlerQuery query) {
        // 查询部门列表
        PageInfo<SysDept> deptList = sysDeptService.deptList();
        long total = deptList.getTotal();

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<SysDept> handlerFunDto = new HandlerFunDto<>(deptList.getList(), total)
                .setStorageId(dept -> "dept:" + dept.getId()) // 前面拼接dept:  是为了防止用户、部门的主键重复
                .setHandlerName(SysDept::getDeptName) // 权限名称
                .setHandlerCode(SysDept::getCode)
                .setCreateTime(dept -> DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, dept.getCreateTime()));

        return getHandlerSelectVo(handlerFunDto);

    }

    /**
     * 获取用户列表, 同时构建左侧部门树状结构
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    private HandlerSelectVo getUser(HandlerQuery query) {
        // 查询用户列表
        List<SysUser> userList = sysUserService.selectUserList(null);
        long total = new PageInfo<>(userList).getTotal();
        // 查询部门列表，构建树状结构
        List<SysDept> deptList = sysDeptService.deptList().getList();

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<SysUser> handlerFunDto = new HandlerFunDto<>(userList, total)
                .setStorageId(user -> user.getId().toString())
                .setHandlerCode(SysUser::getAccount) // 权限编码
                .setHandlerName(SysUser::getUserName) // 权限名称
                .setCreateTime(user -> DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, user.getCreateTime()))
                .setGroupName(user -> user.getDeptId() != null ? sysDeptService.getByDeptId(user.getDeptId()).getDeptName() : "");

        // 业务系统机构，转成组件内部左侧树列表能够显示的数据
        TreeFunDto<SysDept> treeFunDto = new TreeFunDto<>(deptList)
                .setId(dept -> dept.getId().toString()) // 左侧树ID
                .setName(SysDept::getDeptName) // 左侧树名称
                .setParentId(dept -> dept.getParentDeptId().toString()); // 左侧树父级ID

        return getHandlerSelectVo(handlerFunDto, treeFunDto);
    }


}
