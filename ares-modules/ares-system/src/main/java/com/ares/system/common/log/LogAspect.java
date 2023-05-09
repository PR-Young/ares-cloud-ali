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

package com.ares.system.common.log;

import com.alibaba.fastjson.JSON;
import com.ares.core.model.system.SysLog;
import com.ares.core.model.system.SysUser;
import com.ares.core.utils.*;
import com.ares.log.common.Log;
import com.ares.security.common.SecurityUtils;
import com.ares.system.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.Map;

/**
 * @description: 用户日志记录 当前仅记录出错日志
 * @author: Young 2020/01/27
 **/
@Aspect
@Component
@EnableAsync
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Date> dateThreadLocal = new ThreadLocal<>();
    private SysLogService sysLogService;

    @Autowired
    public LogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Pointcut("@annotation(com.ares.log.common.Log)")
    public void logPointCut() {
    }

    @Pointcut("bean(*Controller)")
    public void logPointCutController() {
    }

    @Pointcut("execution(* com.ares.*.service.*.*(..))")
    public void logPointCutService() {
    }

    @AfterReturning("logPointCutController() || logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    //出错时记录日志
    @AfterThrowing(value = "logPointCutController() || logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    @Async
    public void handleLog(JoinPoint joinPoint, Exception e) {
        logger.info("用户行为日志记录开始！");
        dateThreadLocal.remove();
        dateThreadLocal.set(new Date());
        logger.info("开始计时:{}", DateUtils.getTime());
        SysLog sysLog = new SysLog();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log annotation = method.getAnnotation(Log.class);
            Log ignoreClazz = method.getDeclaringClass().getAnnotation(Log.class);

            if (null != ignoreClazz || null != annotation) {
                if (ignoreClazz.isIgnore() || annotation.isIgnore()) {
                    return;
                }
                sysLog.setUrl(method.getDeclaringClass().getName() + "." + method.getName())
                        .setOperParams(setMethodParameters(joinPoint, method));
                if (null != e) {
                    sysLog.setNotes(StringUtils.substring(e.getMessage(), 0, 2000));
                }
            } else {
                String url = ServletUtils.getRequest().getRequestURI();
                String ip = IpUtils.getIpAddr(ServletUtils.getRequest());

                SysUser currentUser = SecurityUtils.getUser();
                if (null != currentUser) {
                    sysLog.setHostIp(ip)
                            .setUrl(url)
                            .setUserName(currentUser.getUserName())
                            .setRequestMethod(ServletUtils.getRequest().getMethod());
                    if (null != e) {
                        sysLog.setNotes(StringUtils.substring(e.getMessage(), 0, 2000));
                    } else {
                        setRequestValue(joinPoint, sysLog);
                    }
                }
            }
            long beginTime = dateThreadLocal.get().getTime();
            long endTime = System.currentTimeMillis();
            logger.info("计时结束:{} 耗时:{}", DateUtils.format(endTime), (endTime - beginTime) / 1000 + "s");
        } catch (Exception ex) {
            sysLog.setNotes(StringUtils.substring(ex.getMessage(), 0, 2000));
        } finally {
            if (null != sysLog.getUrl()) {
                ThreadPoolUtils.executorService.execute(new SaveLogThread(sysLog, sysLogService));
            }
            logger.info("用户行为日志记录结束！");
        }
    }

    private void setRequestValue(JoinPoint joinPoint, SysLog sysLog) {
        String requestMethod = sysLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            sysLog.setOperParams(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            sysLog.setOperParams(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    private boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

    private String setMethodParameters(JoinPoint joinPoint, Method method) {
        StringBuffer sb = new StringBuffer();
        Parameter[] parameters = method.getParameters();
        Object[] values = joinPoint.getArgs();
        if (null != parameters && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                sb.append("{").append(parameters[i].getName()).append("=");
                if (null != values && values.length > 0) {
                    sb.append(values[i]).append("}");
                }
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
