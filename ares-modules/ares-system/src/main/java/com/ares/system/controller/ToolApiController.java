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

package com.ares.system.controller;


import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.server.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 工具
 * @author: Young 2020/05/06
 **/
@Controller
@RequestMapping("/")
@Tag(name = "ToolApiController", description = "通用工具")
public class ToolApiController {

    @RequestMapping("monitor/druid/index")
    @Operation(summary = "druid监控", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    public String druid() {
        return "redirect:/druid";
    }

    @RequestMapping("monitor/actuator/index")
    @Operation(summary = "actuator监控", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    public String actuator() {
        return "redirect:/actuator";
    }

    @RequestMapping("monitor/server")
    @ResponseBody
    @Operation(summary = "服务器信息", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object server() throws Exception {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.successData(server);
    }

    @RequestMapping("tool/swagger/index")
    @Operation(summary = "swagger接口", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping("tool/knife4j/index")
    @Operation(summary = "knife4j接口", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    public String knife4j() {
        return "redirect:/doc.html";
    }

}
