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


import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.utils.DateUtils;
import com.ares.flow.model.FwDefinition;
import com.ares.flow.persistence.service.FlowDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.core.service.ChartService;
import org.dromara.warm.flow.core.service.DefService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@Slf4j
@RestController
@RequestMapping("/aresflow/definition")
public class FlowDefinitionApiController extends BaseController {
    private FlowDefinitionService flowDefinitionService;
    private DefService defService;
    private ChartService chartService;

    @Autowired
    public FlowDefinitionApiController(DefService defService, ChartService chartService, FlowDefinitionService flowDefinitionService) {
        this.defService = defService;
        this.chartService = chartService;
        this.flowDefinitionService = flowDefinitionService;
    }

    @GetMapping("list")
    public AjaxResult getFlowList(FwDefinition fwDefinition) {
        FlowDefinition definition = new FlowDefinition();
        definition.setFlowName(fwDefinition.getFlowName());
        definition.setCreateTime(DateUtils.parseDate(fwDefinition.getCreateTime()));
        Page<Definition> page = new Page<>();
        page.setPageNum(fwDefinition.getPageNum());
        page.setPageSize(fwDefinition.getPageSize());
        return AjaxResult.successData(defService.page(definition, page));
    }

    @PostMapping("save")
    public AjaxResult saveFlow(@RequestBody FlowDefinition definition) {
        defService.saveAndInitNode(definition);
        return AjaxResult.success();
    }

    @PostMapping("checkAndSave")
    public AjaxResult checkAndSave(@RequestBody FlowDefinition definition) {
        defService.checkAndSave(definition);
        return AjaxResult.success();
    }


    @DeleteMapping("{ids}")
    public AjaxResult deleteFlow(@PathVariable Long[] ids) {
        defService.removeByIds(Arrays.asList(ids));
        return AjaxResult.success();
    }

    @RequestMapping("publish/{id}")
    public AjaxResult publishFlow(@PathVariable Long id) {
        defService.publish(id);
        return AjaxResult.success();
    }

    @RequestMapping("unpublish/{id}")
    public AjaxResult unpublishFlow(@PathVariable Long id) {
        defService.unPublish(id);
        return AjaxResult.success();
    }

    @RequestMapping("active/{id}")
    public AjaxResult activeFlow(@PathVariable Long id) {
        defService.active(id);
        return AjaxResult.success();
    }

    @RequestMapping("unactive/{id}")
    public AjaxResult unActiveFlow(@PathVariable Long id) {
        defService.unActive(id);
        return AjaxResult.success();
    }

    @RequestMapping("copy/{id}")
    public AjaxResult copyFlow(@PathVariable Long id) {
        defService.copyDef(id);
        return AjaxResult.success();
    }

    @RequestMapping("del/{ids}")
    public AjaxResult delFlow(@PathVariable Long[] ids) {
        defService.removeByIds(Arrays.asList(ids));
        return AjaxResult.success();
    }

    /**
     * 查询流程图
     *
     * @param instanceId
     * @return
     */
    @GetMapping("flowChart/{instanceId}")
    public AjaxResult flowChart(@PathVariable("instanceId") Long instanceId) {
        return AjaxResult.successData(flowDefinitionService.getFlowChart(instanceId));
    }

    @GetMapping(value = "activeFlowList")
    public Object list() {
        FlowDefinition definition = new FlowDefinition();
        definition.setIsPublish(1);
        definition.setActivityStatus(1);
        return AjaxResult.successData(defService.list(definition));
    }
    @RequestMapping("updatePublishStatus/{ids}/{status}")
    public AjaxResult updatePublishStatus(@PathVariable Long[] ids, @PathVariable Integer status) {
        defService.updatePublishStatus(Arrays.asList(ids), status);
        return AjaxResult.success();
    }

}
