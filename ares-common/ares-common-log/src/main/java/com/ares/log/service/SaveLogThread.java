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

package com.ares.log.service;


import com.ares.api.client.ISysLogService;
import com.ares.core.model.system.SysLog;

/**
 * @description:
 * @author: Young 2020/05/08
 **/
public class SaveLogThread implements Runnable {
    private SysLog sysLog;
    private ISysLogService sysLogService;

    public SaveLogThread(SysLog sysLog, ISysLogService sysLogService) {
        this.sysLog = sysLog;
        this.sysLogService = sysLogService;
    }

    @Override
    public void run() {
        sysLogService.insert(sysLog);
    }
}
