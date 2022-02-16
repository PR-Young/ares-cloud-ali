package com.ares.security.jwt;


import com.ares.core.utils.MD5Util;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description:
 * @author: Young
 * @date: 2020/10/19
 * @see: com.ares.security.jwt JwtAuthenticationProvider.java
 **/
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {
    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setHideUserNotFoundExceptions(false);
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 可以在此处覆写整个登录认证逻辑
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        // 可以在此处覆写密码验证逻辑
        String password = userDetails.getPassword();
        String pwd = authentication.getCredentials().toString();

        if (null != userDetails) {
            if (!password.equals(MD5Util.encode(pwd))) {
                throw new BadCredentialsException("用户名密码错误");
            }
        }
    }
}
