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

package com.ares.auth.controller;


import com.ares.api.client.ISysLoginInfoService;
import com.ares.core.config.base.BaseConfig;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.Constants;
import com.ares.core.model.system.SysLoginInfo;
import com.ares.core.utils.AresCommonUtils;
import com.ares.core.utils.IpUtils;
import com.ares.core.utils.ServletUtils;
import com.ares.redis.utils.RedisUtil;
import com.ares.security.common.SecurityUtils;
import com.ares.security.jwt.JwtAuthenticationToken;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @author: Young
 * @date: 2025/4/1
 * @see: com.ares.auth.controller.AuthController.java
 **/
@RestController
public class AuthController {

    private static final long EXPIRE = 30 * 24 * 60 * 60;


    private BaseConfig config;
    private AuthenticationManager authenticationManager;
    @DubboReference(version = "1.0.0", interfaceClass = com.ares.api.client.ISysLoginInfoService.class, check = false)
    private ISysLoginInfoService loginInfoService;

    @Autowired
    public AuthController(BaseConfig config,
            AuthenticationManager authenticationManager) {
        this.config = config;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("login")
    public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = ServletUtils.getParameter();
        String userName = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        String code = String.valueOf(map.get("code"));
        String uuid = String.valueOf(map.get("uuid"));
        Boolean rememberMe = Boolean.parseBoolean(String.valueOf(map.get("rememberMe")));

        if (!checkVerifyCode(code, uuid)) {
            return AjaxResult.error(500, "验证码错误");
        }

        // 系统登录认证
        JwtAuthenticationToken token = SecurityUtils.login(request, userName, password, authenticationManager);
        if (rememberMe) {
            RedisUtil.set(Constants.LOGIN_INFO + userName, token, EXPIRE);
        } else {
            RedisUtil.set(Constants.LOGIN_INFO + userName, token, config.getTimeout());
        }
        SysLoginInfo sysLoginInfo = new SysLoginInfo();
        sysLoginInfo.setUserName(userName);
        sysLoginInfo.setLoginTime(new Date());
        sysLoginInfo.setIpAddr(IpUtils.getIpAddr(request));
        sysLoginInfo.setStatus(Constants.ONLINE);
        sysLoginInfo.setBrowser(AresCommonUtils.getUserAgent(request, "browser"));
        sysLoginInfo.setOs(AresCommonUtils.getUserAgent(request, "os"));
        Long id = loginInfoService.saveInfo(sysLoginInfo);
        RedisUtil.set(token.getToken(), id, config.getTimeout());
        return AjaxResult.success().put("token", token.getToken());
    }


    @RequestMapping("unAuth")
    @Operation(summary = "未登录", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object unAuth(HttpServletRequest request, HttpServletResponse response) {
        return AjaxResult.unLogin();
    }

    @RequestMapping("unauthorized")
    @Operation(summary = "无权限", responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Object.class)))})
    public Object unauthorized(HttpServletRequest request, HttpServletResponse response) {
        return AjaxResult.error(HttpStatus.UNAUTHORIZED.value(), "用户无权限！");
    }

    @RequestMapping("/kaptcha")
    public Object getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(120, 40);
        String code = arithmeticCaptcha.text();
        String uuid = UUID.randomUUID().toString();
        RedisUtil.set(Constants.KAPTCHA_SESSION_KEY + uuid, code, 120);
        return AjaxResult.success().put("uuid", uuid).put("img", arithmeticCaptcha.toBase64());
    }

    /**
     * 校验验证码是否有效
     *
     * @param code
     * @param uuid
     * @return
     */
    public static boolean checkVerifyCode(String code, String uuid) {
        Object verifyCodeActual = RedisUtil.get(Constants.KAPTCHA_SESSION_KEY + uuid);
        if (null == verifyCodeActual || !verifyCodeActual.equals(code)) {
            return false;
        }
        return true;
    }
}
