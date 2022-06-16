package com.ares.api.client.fallback;

import com.ares.api.client.ISysDeptService;
import com.ares.core.model.base.JsonResult;
import com.ares.core.model.system.SysDept;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/15
 * @see: com.ares.api.client.fallback.SysDeptServiceImpl.java
 **/
public class SysDeptServiceImpl implements ISysDeptService {
    @Override
    public JsonResult<SysDept> getByDeptId(String id) {
        return JsonResult.error(null, 1000, "error");
    }
}
