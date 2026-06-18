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

package com.ares.flow.controller;


import com.ares.core.model.base.AjaxResult;
import com.ares.flow.persistence.model.StartFlowParam;
import com.ares.flow.persistence.service.FlowInstanceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.service.InsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@Tag(name = "FlowInstanceApiController", description = "工作流流程实例管理")
@RestController
@RequestMapping("/aresflow/instance")
public class FlowInstanceApiController {

    private InsService insService;
    private FlowInstanceService flowInstanceService;

    @Autowired
    public FlowInstanceApiController(InsService insService,
                                  FlowInstanceService flowInstanceService) {
        this.insService = insService;
        this.flowInstanceService = flowInstanceService;
    }

    @PostMapping("start")
    public AjaxResult startFlow(@RequestBody StartFlowParam params) {
        return flowInstanceService.start(params);
    }


    @RequestMapping(value = "active/{id}")
    public AjaxResult active(@Parameter Long id) {
        insService.active(id);
        return AjaxResult.success();
    }

    @RequestMapping(value = "unactive/{id}")
    public AjaxResult unActive(@Parameter Long id) {
        insService.unActive(id);
        return AjaxResult.success();
    }

    @DeleteMapping("remove/{ids}")
    public AjaxResult stopProcessInstance(@PathVariable Long[] ids) {
        insService.remove(Arrays.asList(ids));
        return AjaxResult.success();
    }

}
