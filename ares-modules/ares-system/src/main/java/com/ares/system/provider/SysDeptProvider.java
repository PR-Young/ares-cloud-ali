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

package com.ares.system.provider;

import com.ares.api.client.ISysDeptService;
import com.ares.core.model.system.SysDept;
import com.ares.system.service.SysDeptService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: Young
 * @date: 2023/5/19
 * @see: com.ares.system.provider.SysDeptProvider.java
 **/
@DubboService
public class SysDeptProvider implements ISysDeptService {

    @Autowired
    SysDeptService deptService;
    @Override
    public SysDept getByDeptId(Long id) {
        return deptService.getByDeptId(id);
    }
}
