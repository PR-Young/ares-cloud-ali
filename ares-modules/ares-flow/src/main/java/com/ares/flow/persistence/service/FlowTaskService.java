/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

package com.ares.flow.persistence.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ares.api.client.ISysDeptService;
import com.ares.api.client.ISysRoleService;
import com.ares.api.client.ISysUserService;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.system.SysDept;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.flow.model.query.FlowTaskQuery;
import com.ares.flow.persistence.dao.IFlowTaskDao;
import com.ares.flow.persistence.model.FlowTask;
import com.ares.flow.persistence.model.SysForm;
import com.ares.flow.persistence.model.SysFormData;
import com.ares.security.common.SecurityUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.entity.HisTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: Young
 * @date: 2022/1/13
 * @see: com.ares.flow.persistence.service.FlowTaskService.java
 **/
@Service
public class FlowTaskService {
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysUserService.class, check = false)
    private ISysUserService userService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysRoleService.class, check = false)
    private ISysRoleService roleService;
    private SysDeployFormService deployFormService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysDeptService.class, check = false)
    private ISysDeptService deptService;
    private SysFormDataService formDataService;
    private IFlowTaskDao flowTaskDao;

    @Autowired
    public FlowTaskService(
            SysDeployFormService deployFormService,
            SysFormDataService formDataService,
            IFlowTaskDao flowTaskDao) {
        this.deployFormService = deployFormService;
        this.formDataService = formDataService;
        this.flowTaskDao = flowTaskDao;
    }

    public AjaxResult flowRecord(String procInsId, String deployId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 第一次申请获取初始化表单
        if (StringUtils.isNotBlank(deployId)) {
            SysForm sysForm = deployFormService.selectSysDeployFormByDeployId(deployId);
            if (Objects.isNull(sysForm)) {
                return AjaxResult.error("请先配置流程表单");
            }
            if (StringUtils.isNotBlank(procInsId)) {
                JSONObject jsonObject = fillData(sysForm.getFormContent(), procInsId);
                map.put("formData", jsonObject);
            } else {
                map.put("formData", JSONObject.parseObject(sysForm.getFormContent()));
            }
        }
        if (StringUtils.isNotBlank(procInsId)) {
            List<HisTask> hisTasks = FlowEngine.hisTaskService().getByInsId(Long.valueOf(procInsId));
            List<Map<String, Object>> hisLists = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(hisTasks)) {
                for (HisTask hisTask : hisTasks) {
                    Map<String, Object> hisMap = new HashMap<>();
                    hisMap.put("taskId", hisTask.getTaskId());
                    hisMap.put("nodeName", hisTask.getNodeName());
                    hisMap.put("flowStatus", hisTask.getFlowStatus());
                    hisMap.put("message", hisTask.getMessage());
                    SysUser user = userService.getById(Long.valueOf(hisTask.getApprover()));
                    hisMap.put("approver", user.getUserName());
                    if (StringUtils.isNotBlank(hisTask.getCollaborator())) {
                        String[] ids = hisTask.getCollaborator().split(",");
                        String collaborator = "";
                        for (String id : ids) {
                            SysUser sysUser = userService.getById(Long.valueOf(id));
                            collaborator += sysUser.getUserName() + " ";
                        }
                        hisMap.put("collaborator", collaborator);
                    }
                    hisMap.put("cooperateType", hisTask.getCooperateType());
                    hisMap.put("createTime", hisTask.getCreateTime());
                    hisMap.put("updateTime", hisTask.getUpdateTime());
                    hisMap.put("duration", (hisTask.getUpdateTime().getTime() - hisTask.getCreateTime().getTime()) / 1000);
                    hisMap.put("ext", hisTask.getExt());
                    hisLists.add(hisMap);
                }
            }
            map.put("hisTasks", hisLists);
        }
        return AjaxResult.successData(map);
    }


    public PageInfo<FlowTask> todoList(FlowTaskQuery query) {
        SysUser user = SecurityUtils.getUser();
        List<String> params = new ArrayList<>();
        for (Long roleId : user.getRoleIds()) {
            params.add("role:" + roleId);
        }
        params.add(String.valueOf(user.getId()));
        params.add("dept:" + user.getDeptId());
        List<FlowTask> todoList = flowTaskDao.getTodoList(params);
        PageInfo<FlowTask> pageInfo = new PageInfo<>(todoList);
        return pageInfo;
    }


    public PageInfo<FlowTask> myProcess(FlowTaskQuery query) {
        Long userId = SecurityUtils.getUser().getId();
        List<FlowTask> myProcess = flowTaskDao.getMyProcess(userId);
        myProcess.stream().forEach(process -> process.setPermissionFlag(transPermissionFlag(process.getPermissionFlag())));
        PageInfo<FlowTask> pageInfo = new PageInfo<>(myProcess);
        return pageInfo;
    }


    public PageInfo<FlowTask> finishedList(FlowTaskQuery query) {
        Long userId = SecurityUtils.getUser().getId();
        List<FlowTask> myProcess = flowTaskDao.finishedList(userId);
        PageInfo<FlowTask> pageInfo = new PageInfo<>(myProcess);
        return pageInfo;
    }

    private JSONObject fillData(String formContent, String procInsId) {
        SysFormData sysFormData = formDataService.getFormDataByProInstId(procInsId);
        String formData = sysFormData.getFormData();
        JSONObject data = JSONObject.parseObject(formData);
        JSONObject jsonObject = JSONObject.parseObject(formContent);
        JSONArray fields = jsonObject.getJSONArray("fields");
        jsonObject.put("formData", data);
        for (int i = 0; i < fields.size(); i++) {
            JSONObject field = fields.getJSONObject(i);
            JSONObject config = field.getJSONObject("__config__");
            if (null != config) {
                String vModel = field.getString("__vModel__");
                config.put("defaultValue", data.get(vModel));
            }
        }
        return jsonObject;
    }

    private String transPermissionFlag(String permissionFlag) {
        String result = "";
        if (StringUtils.isNotBlank(permissionFlag)) {
            String[] flags = permissionFlag.split("@@");
            for (String flag : flags) {
                String[] split = flag.split(":");
                switch (split[0]) {
                    case "role":
                        SysRole role = roleService.getById(Long.valueOf(split[1]));
                        result = result + role.getRoleName() + ",";
                        break;
                    case "dept":
                        SysDept dept = deptService.getByDeptId(Long.valueOf(split[1]));
                        result = result + dept.getDeptName() + ",";
                        break;
                    default:
                        SysUser user = userService.getById(Long.valueOf(split[0]));
                        result = result + user.getUserName() + ",";
                        break;
                }
            }
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

}
