package com.ares.system.controller;

import com.ares.core.controller.BaseController;
import com.ares.core.persistence.model.base.AjaxResult;
import ${entityPackage}.${entityName};
import com.ares.core.persistence.model.page.TableDataInfo;
import ${servicePackage}.${entityName}Service;
import com.ares.core.utils.StringUtils;
import com.ares.system.common.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/${entityName1}/*")
@Api(value = "API", tags = {"管理"})
public class ${entityName}ApiController extends BaseController {

private ${entityName}Service ${entityName1}Service;

@Autowired
public ${entityName}ApiController(${entityName}Service ${entityName1}Service){
this.${entityName1}Service = ${entityName1}Service;
}

@PreAuthorize("hasAnyAuthority('${entityName1}:list')")
@RequestMapping("list")
@ApiOperation(value = "列表", response = TableDataInfo.class)
public TableDataInfo list(${entityName} ${entityName1}) {
startPage();
List<${entityName}> ${entityName1}List = ${entityName1}Service.list(${entityName1});
return getDataTable(${entityName1}List);
}

@GetMapping("{${entityName1}Id}")
@ApiOperation(value = "根据Id获取信息", response = Object.class)
public Object getInfo(@PathVariable Long ${entityName1}Id) {
return AjaxResult.successData(${entityName1}Service.getById(${entityName1}Id));
}

@PreAuthorize("hasAnyAuthority('${entityName1}:edit')")
@PostMapping("edit")
@ApiOperation(value = "编辑信息", response = Object.class)
public Object edit(@Validated @RequestBody ${entityName} ${entityName1}) throws Exception{
if (StringUtils.isEmpty(${entityName1}.getId())) {
${entityName1}.setCreator(SecurityUtils.getUser().getId());
${entityName1}Service.insert(${entityName1});
} else {
${entityName1}.setModifier(SecurityUtils.getUser().getId());
${entityName1}Service.update(${entityName1});
}
return AjaxResult.success();
}

@PreAuthorize("hasAnyAuthority('${entityName1}:delete')")
@DeleteMapping("{${entityName1}Ids}")
@ApiOperation(value = "删除信息", response = Object.class)
public Object remove(@PathVariable Long[] ${entityName1}Ids) {
${entityName1}Service.deleteByIds(Arrays.asList(${entityName1}Ids));
return AjaxResult.success();
}
}
