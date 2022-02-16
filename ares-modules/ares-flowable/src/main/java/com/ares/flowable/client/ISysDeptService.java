package com.ares.flowable.client;

import com.ares.core.model.system.SysDept;
import com.ares.flowable.client.fallback.SysDeptServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/15
 * @see: com.ares.flowable.client.fallback.ISysDeptService.java
 **/
@FeignClient(name = "ares-system",fallback = SysDeptServiceImpl.class)
public interface ISysDeptService {
    @GetMapping("/sysDept/{id}")
    public SysDept getByDeptId(@PathVariable(value = "id") String id);
}
