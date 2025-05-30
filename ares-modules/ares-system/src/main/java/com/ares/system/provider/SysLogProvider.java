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


import com.ares.api.client.ISysLogService;
import com.ares.core.model.system.SysLog;
import com.ares.system.service.SysLogService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: Young
 * @date: 2025/4/1
 * @see: com.ares.system.provider.SysLogProvider.java
 **/
@DubboService(version = "1.0.0",interfaceClass = com.ares.api.client.ISysLogService.class)
public class SysLogProvider implements ISysLogService {

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void insert(SysLog sysLog) {
        sysLogService.insert(sysLog);
    }
}
