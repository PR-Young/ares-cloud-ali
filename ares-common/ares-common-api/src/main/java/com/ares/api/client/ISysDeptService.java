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
    @GetMapping("/system/sysDept/getByDeptId/{id}")
    JsonResult<SysDept> getByDeptId(@PathVariable(value = "id") String id);
}