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

package com.ares.flowable.persistence.service;


import cn.hutool.json.JSONUtil;
import com.ares.api.client.ISysDeptService;
import com.ares.api.client.ISysPostService;
import com.ares.api.client.ISysUserService;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.system.SysUser;
import com.ares.flowable.common.constant.ProcessConstants;
import com.ares.flowable.factory.FlowServiceFactory;
import com.ares.flowable.persistence.model.SysForm;
import com.ares.flowable.persistence.model.SysFormData;
import com.ares.flowable.persistence.model.dto.FlowProcDefDto;
import com.ares.security.common.SecurityUtils;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class FlowDefinitionService extends FlowServiceFactory {
    private SysDeployFormService sysDeployFormService;
    @DubboReference
    private ISysUserService sysUserService;
    @DubboReference
    private ISysDeptService sysDeptService;
    @DubboReference
    private ISysPostService postService;
    private SysFormDataService formDataService;

    @Autowired
    public FlowDefinitionService(SysDeployFormService deployFormService,
                                 SysFormDataService formDataService) {
        this.sysDeployFormService = deployFormService;
        this.formDataService = formDataService;
    }

    private static final String BPMN_FILE_SUFFIX = ".bpmn";


    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery
                = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);
        long count = processDefinitionQuery.count();
        return count > 0 ? true : false;
    }


    /**
     * 流程定义列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 流程定义分页列表数据
     */
    public Page<FlowProcDefDto> list(Integer pageNum, Integer pageSize) {
        Page<FlowProcDefDto> page = new Page<>();
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .orderByProcessDefinitionKey().asc();
        page.setTotal(processDefinitionQuery.count());
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageNum - 1, pageSize);
        List<FlowProcDefDto> dataList = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            FlowProcDefDto reProcDef = new FlowProcDefDto();
            BeanUtils.copyProperties(processDefinition, reProcDef);
            SysForm sysForm = sysDeployFormService.selectSysDeployFormByDeployId(deploymentId);
            if (Objects.nonNull(sysForm)) {
                reProcDef.setFormName(sysForm.getFormName());
                reProcDef.setFormId(String.valueOf(sysForm.getId()));
            }
            // 流程定义时间
            reProcDef.setDeploymentTime(deployment.getDeploymentTime());
            dataList.add(reProcDef);
        }

        page.addAll(dataList);
        return page;
    }


    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    public void importFile(String name, String category, InputStream in) {
        Deployment deploy = repositoryService.createDeployment().addInputStream(name + BPMN_FILE_SUFFIX, in).name(name).category(category).deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), category);

    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    public AjaxResult readXml(String deployId) throws IOException {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        InputStream inputStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return AjaxResult.successData(result);
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    public InputStream readImage(String deployId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        //获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //输出为图片
        return diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                Collections.emptyList(),
                Collections.emptyList(),
                "宋体",
                "宋体",
                "宋体",
                null,
                1.0,
                false);

    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     * @return
     */
    public AjaxResult startProcessInstanceById(String procDefId, Map<String, Object> variables) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId)
                    .latestVersion().singleResult();
            if (Objects.nonNull(processDefinition) && processDefinition.isSuspended()) {
                return AjaxResult.error("流程已被挂起,请先激活流程");
            }
            // 设置流程发起人Id到流程中
            SysUser sysUser = SecurityUtils.getUser();
            identityService.setAuthenticatedUserId(String.valueOf(sysUser.getId()));
            variables.put(ProcessConstants.PROCESS_INITIATOR, sysUser.getId());
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);

            //保存表单数据
            SysFormData formData = new SysFormData();
            formData.setProInstId(processInstance.getProcessInstanceId());
            formData.setFormData(JSONUtil.toJsonStr(variables));
            formData.setCreator(sysUser.getId());
            formDataService.insert(formData);

            // 给第一步申请人节点设置任务执行人和意见 todo:第一个节点不设置为申请人节点有点问题？
            //Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            //if (Objects.nonNull(task)) {
            //   // taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(), sysUser.getUserName() + "发起流程申请");
            //    taskService.setOwner(task.getId(), sysUser.getId());
            //    //taskService.complete(task.getId(), variables);
            //}
            return AjaxResult.success("流程启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("流程启动错误");
        }
    }


    /**
     * 激活或挂起流程定义
     *
     * @param state    状态
     * @param deployId 流程部署ID
     */
    public void updateState(Integer state, String deployId) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        // 激活
        if (state == 1) {
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, null);
        }
        // 挂起
        if (state == 2) {
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, null);
        }
    }


    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    public void delete(String deployId) {
        // true 允许级联删除 ,不设置会导致数据库外键关联异常
        repositoryService.deleteDeployment(deployId, true);
    }

    /**
     * 发起流程列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<FlowProcDefDto> applyFlowList(Integer pageNum, Integer pageSize) {
        Page<FlowProcDefDto> page = new Page<>();
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .active()
                .orderByProcessDefinitionKey().asc();
        page.setTotal(processDefinitionQuery.count());
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageNum - 1, pageSize);
        List<FlowProcDefDto> dataList = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            FlowProcDefDto reProcDef = new FlowProcDefDto();
            BeanUtils.copyProperties(processDefinition, reProcDef);
            SysForm sysForm = sysDeployFormService.selectSysDeployFormByDeployId(deploymentId);
            if (Objects.nonNull(sysForm)) {
                reProcDef.setFormName(sysForm.getFormName());
                reProcDef.setFormId(String.valueOf(sysForm.getId()));
            }
            // 流程定义时间
            reProcDef.setDeploymentTime(deployment.getDeploymentTime());
            dataList.add(reProcDef);
        }

        page.addAll(dataList);
        return page;
    }

    /**
     * 发起流程列表
     *
     * @return
     */
    public List<FlowProcDefDto> applyFlowList() {
        List<FlowProcDefDto> dataList = new ArrayList<>();
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .active()
                .orderByProcessDefinitionKey().asc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            FlowProcDefDto reProcDef = new FlowProcDefDto();
            BeanUtils.copyProperties(processDefinition, reProcDef);
            SysForm sysForm = sysDeployFormService.selectSysDeployFormByDeployId(deploymentId);
            if (Objects.nonNull(sysForm)) {
                reProcDef.setFormName(sysForm.getFormName());
                reProcDef.setFormId(String.valueOf(sysForm.getId()));
            }
            // 流程定义时间
            reProcDef.setDeploymentTime(deployment.getDeploymentTime());
            dataList.add(reProcDef);
        }
        return dataList;
    }

}
