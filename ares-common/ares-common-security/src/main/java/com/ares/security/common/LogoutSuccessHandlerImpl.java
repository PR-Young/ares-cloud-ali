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

package com.ares.security.common;

import com.ares.api.client.IAresCommonService;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.Constants;
import com.ares.core.model.system.SysLoginInfo;
import com.ares.core.utils.ServletUtils;
import com.ares.core.utils.SpringUtils;
import com.ares.redis.utils.RedisUtil;
import com.ares.security.jwt.JwtTokenUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @description:
 * @author: Young
 * @date: 2020/10/19
 * @see: com.ares.security.security LogoutSuccessHandlerImpl.java
 **/
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = JwtTokenUtils.getToken(httpServletRequest);
        if (null != token) {
            String userName = JwtTokenUtils.getUsernameFromToken(token);
            if (RedisUtil.hasKey(Constants.LOGIN_INFO + userName)) {
                Long id = Long.valueOf(String.valueOf(RedisUtil.get(token)));
                SysLoginInfo sysLoginInfo = new SysLoginInfo();
                sysLoginInfo.setId(id);
                sysLoginInfo.setStatus(Constants.OFFLINE);
                IAresCommonService commonService = SpringUtils.getBean("aresCommonProvider");
                commonService.update(sysLoginInfo);
                RedisUtil.del(Constants.LOGIN_INFO + userName);
            }
        }
        ServletUtils.writeResponse(httpServletResponse, AjaxResult.success());
    }
}
