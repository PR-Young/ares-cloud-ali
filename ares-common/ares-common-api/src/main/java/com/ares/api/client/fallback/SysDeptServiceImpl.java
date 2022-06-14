package com.ares.api.client.fallback;

import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysDept;
import com.ares.api.client.ISysDeptService;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/15
 * @see: com.ares.api.client.fallback.SysDeptServiceImpl.java
 **/
public class SysDeptServiceImpl implements ISysDeptService {
    @Override
    public JsonResult<SysDept> getByDeptId(String id) {
        return null;
    }
}
