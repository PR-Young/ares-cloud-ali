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

import com.ares.core.config.base.BaseConfig;
import com.ares.security.jwt.JwtAuthenticationFilter;
import com.ares.security.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @description:
 * @author: Young
 * @date: 2020/10/19
 * @see: com.ares.security.security WebSecurityConfig.java
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private UserDetailsService userDetailsService;
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    private BaseConfig config;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                             LogoutSuccessHandlerImpl logoutSuccessHandler,
                             AuthenticationEntryPointImpl authenticationEntryPoint,
                             BaseConfig config) {
        this.userDetailsService = userDetailsService;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.config = config;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        String[] whiteUrl = config.getWhiteUrl().split(",");
        AntPathRequestMatcher[] matchers = new AntPathRequestMatcher[whiteUrl.length];
        for (int i = 0; i < whiteUrl.length; i++) {
            matchers[i] = AntPathRequestMatcher.antMatcher(whiteUrl[i]);
        }
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "*/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "*/**")).permitAll()
                            .requestMatchers(matchers).permitAll()
                            .anyRequest().authenticated();
                })
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        httpSecurity.logout(lg -> lg.logoutUrl("/loginOut").logoutSuccessHandler(logoutSuccessHandler));
        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        return new JwtAuthenticationProvider(userDetailsService);
    }


}
