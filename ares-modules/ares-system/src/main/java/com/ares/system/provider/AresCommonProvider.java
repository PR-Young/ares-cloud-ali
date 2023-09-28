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

import com.ares.api.client.IAresCommonService;
import com.ares.core.model.system.SysLoginInfo;
import com.ares.system.service.SysLoginInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import jakarta.annotation.Resource;

/**
 * @description:
 * @author: Young
 * @date: 2023/8/28
 * @see: com.ares.system.provider.AresCommonProvider.java
 **/
@DubboService(version = "1.0.0",interfaceClass = com.ares.api.client.IAresCommonService.class)
public class AresCommonProvider implements IAresCommonService {

    @Resource
    SysLoginInfoService loginInfoService;

    @Override
    public void update(SysLoginInfo loginInfo) {
        loginInfoService.update(loginInfo);
    }
}
