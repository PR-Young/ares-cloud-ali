package com.ares.api.client;

import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysDept;
import com.ares.api.client.fallback.SysDeptServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/15
 * @see: com.ares.api.client.fallback.ISysDeptService.java
 **/
@FeignClient(name = "ares-system", fallback = SysDeptServiceImpl.class)
public interface ISysDeptService {
    /**
     * 根据Id获取部门信息
     *
     * @param id
     * @return
     */
    @GetMapping("/sysDept/getByDeptId/{id}")
    JsonResult<SysDept> getByDeptId(@PathVariable(value = "id") String id);
}
