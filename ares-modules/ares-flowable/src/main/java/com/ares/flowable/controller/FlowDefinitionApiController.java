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

package com.ares.flowable.controller;

import com.ares.api.client.ISysRoleService;
import com.ares.api.client.ISysUserService;
import com.ares.core.controller.BaseController;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.query.SysRoleQuery;
import com.ares.core.model.query.SysUserQuery;
import com.ares.core.model.system.SysRole;
import com.ares.core.model.system.SysUser;
import com.ares.flowable.persistence.model.dto.FlowSaveXmlVo;
import com.ares.flowable.persistence.service.FlowDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flowable/definition")
public class FlowDefinitionApiController extends BaseController {
    private FlowDefinitionService flowDefinitionService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysUserService.class, check = false)
    private ISysUserService userService;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysRoleService.class, check = false)
    private ISysRoleService sysRoleService;

    @Autowired
    public FlowDefinitionApiController(FlowDefinitionService flowDefinitionService) {
        this.flowDefinitionService = flowDefinitionService;
    }

    @GetMapping(value = "/list")
    @Operation(summary = "流程定义列表", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object list(@Parameter(description = "当前页码", required = true) @RequestParam Integer pageNum,
                       @Parameter(description = "每页条数", required = true) @RequestParam Integer pageSize) {

        return getDataTable(flowDefinitionService.list(pageNum, pageSize));
    }


    @Operation(summary = "导入流程文件", description = "上传bpmn20的xml文件")
    @PostMapping("/import")
    public AjaxResult importFile(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String category,
                                 MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return AjaxResult.success(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }

        return AjaxResult.success("导入成功");
    }


    @Operation(summary = "读取xml文件")
    @GetMapping("/readXml/{deployId}")
    public AjaxResult readXml(@Parameter(description = "流程定义id") @PathVariable(value = "deployId") String deployId) {
        try {
            return flowDefinitionService.readXml(deployId);
        } catch (Exception e) {
            return AjaxResult.error("加载xml文件异常");
        }
    }

    @Operation(summary = "读取图片文件")
    @GetMapping("/readImage/{deployId}")
    public void readImage(@Parameter(description = "流程定义id") @PathVariable(value = "deployId") String deployId, HttpServletResponse response) {
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(flowDefinitionService.readImage(deployId));
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Operation(summary = "保存流程设计器内的xml文件")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody FlowSaveXmlVo vo) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(vo.getXml().getBytes(StandardCharsets.UTF_8));
            flowDefinitionService.importFile(vo.getName(), vo.getCategory(), in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return AjaxResult.success(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流出错", e);
            }
        }
        return AjaxResult.success("导入成功");
    }


    @Operation(summary = "根据流程定义id启动流程实例")
    @PostMapping("/start/{procDefId}")
    public AjaxResult start(@Parameter(description = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                            @Parameter(description = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        return flowDefinitionService.startProcessInstanceById(procDefId, variables);
    }

    @Operation(summary = "激活或挂起流程定义")
    @PutMapping(value = "/updateState")
    public AjaxResult updateState(@Parameter(description = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                                  @Parameter(description = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return AjaxResult.success();
    }

    @Operation(summary = "删除流程")
    @DeleteMapping(value = "/delete")
    public AjaxResult delete(@Parameter(description = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        return AjaxResult.success();
    }

    @Operation(summary = "指定流程办理人员列表")
    @GetMapping("/userList")
    public AjaxResult userList(SysUserQuery user) {
        List<SysUser> list = userService.selectUserList(user);
        return AjaxResult.successData(list);
    }

    @Operation(summary = "指定流程办理组列表")
    @GetMapping("/roleList")
    public AjaxResult roleList(SysRoleQuery role) {
        List<SysRole> list = sysRoleService.selectRoleList(role);
        return AjaxResult.successData(list);
    }

}
