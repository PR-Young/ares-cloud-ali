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