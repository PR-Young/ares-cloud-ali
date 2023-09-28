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
import com.ares.api.client.ISysUserService;
import com.ares.core.exception.UserException;
import com.ares.core.model.base.Constants;
import com.ares.core.model.exception.ErrorCode;
import com.ares.core.model.system.SysLoginInfo;
import com.ares.core.model.system.SysUser;
import com.ares.core.utils.SpringUtils;
import com.ares.redis.utils.RedisUtil;
import com.ares.security.jwt.JwtAuthenticationToken;
import com.ares.security.jwt.JwtTokenUtils;
import com.ares.security.jwt.JwtUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Young
 * @date: 2020/10/19
 * @see: com.ares.security.security SecurityUtils.java
 **/
public class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 系统登录认证
     *
     * @param request
     * @param username
     * @param password
     * @param authenticationManager
     * @return
     */
    public static JwtAuthenticationToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) throws AuthenticationException {
        JwtAuthenticationToken token = new JwtAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 执行登录认证过程
        Authentication authentication = authenticationManager.authenticate(token);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端
        token.setToken(JwtTokenUtils.generateToken(authentication));
        return token;
    }

    /**
     * 获取令牌进行认证
     *
     * @param request
     */
    public static void checkAuthentication(HttpServletRequest request) throws Exception {
        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = JwtTokenUtils.getAuthenticationFromToken(request);
        if (authentication instanceof JwtAuthenticationToken) {
            String token = ((JwtAuthenticationToken) authentication).getToken();
            String userName = JwtTokenUtils.getUsernameFromToken(token);
            if (JwtTokenUtils.isTokenExpired(token)) {
                Long id = Long.valueOf(String.valueOf(RedisUtil.get(token)));
                SysLoginInfo sysLoginInfo = new SysLoginInfo();
                sysLoginInfo.setId(id);
                sysLoginInfo.setStatus(Constants.OFFLINE);
                IAresCommonService commonService = SpringUtils.getBean("aresCommonProvider");
                commonService.update(sysLoginInfo);
                throw new UserException(ErrorCode.LOGINTIMEOUT.getCode(), "登录过期！");
            }
            if (RedisUtil.hasKey(Constants.LOGIN_INFO + userName)) {
                // 设置登录认证信息到上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new UserException(ErrorCode.NOLOGIN.getCode(), "用户未登录！");
            }
        }
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = null;
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null) {
                if (principal instanceof JwtUserDetails) {
                    username = ((UserDetails) principal).getUsername();
                } else if (principal instanceof String) {
                    username = (String) principal;
                }
            }
        }
        return username;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取当前登录信息
     *
     * @return
     */
    public static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }


    public static SysUser getUser() throws UserException {
        String userName = getUsername();
        if("anonymousUser".equals(userName)){
            return null;
        }
        if (null == userName) {
            throw new UserException(ErrorCode.NOUSER.getCode(), "用户不存在！");
        }
        ISysUserService userService = SpringUtils.getBean("sysUserProvider");
        SysUser user = userService.getUserByName(userName);
        return user;
    }

}
