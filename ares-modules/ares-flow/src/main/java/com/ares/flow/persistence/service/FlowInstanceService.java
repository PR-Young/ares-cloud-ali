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


import cn.hutool.json.JSONUtil;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.flow.persistence.model.StartFlowParam;
import com.ares.flow.persistence.model.SysFormData;
import com.ares.security.common.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.service.InsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>工作流流程实例管理<p>
 */
@Service
@Slf4j
public class FlowInstanceService  {

    private InsService insService;
    private SysFormDataService formDataService;

    public FlowInstanceService(InsService insService,
                                   SysFormDataService formDataService) {
        this.insService = insService;
        this.formDataService = formDataService;
    }

    @Transactional
    public AjaxResult start(StartFlowParam params) {
        String businessId = SnowflakeIdWorker.getSUID();
        FlowParams flowParams = FlowParams.build().flowCode(params.getFlowCode()).variable(params.getVariables());
        Instance instance = insService.start(businessId, flowParams);

        //保存表单数据
        SysFormData formData = new SysFormData();
        formData.setProInstId(String.valueOf(instance.getId()));
        formData.setFormData(JSONUtil.toJsonStr(params));
        formData.setCreator(SecurityUtils.getUser().getId());
        formDataService.insert(formData);
        return null;
    }
}
