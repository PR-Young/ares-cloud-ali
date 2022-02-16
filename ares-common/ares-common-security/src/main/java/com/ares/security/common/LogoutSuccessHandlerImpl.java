package com.ares.security.common;

import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.Constants;
import com.ares.core.utils.ServletUtils;
import com.ares.redis.utils.RedisUtil;
import com.ares.security.jwt.JwtTokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                RedisUtil.del(Constants.LOGIN_INFO + userName);
            }
        }
        ServletUtils.writeResponse(httpServletResponse, AjaxResult.success());
    }
}
