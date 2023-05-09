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

package com.ares.system.common.exception;


import com.ares.core.exception.UserException;
import com.ares.core.model.base.AjaxResult;
import com.ares.core.model.base.ResultCode;
import com.ares.core.model.exception.ErrorCode;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Young 2020/05/09
 **/
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class SysGlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public Object handleNotValidException(HttpServletRequest request, HttpServletResponse response, BindException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return AjaxResult.result(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMsg(), error.getDefaultMessage());
    }

    @ExceptionHandler(value = UserException.class)
    public Object handleUserException(HttpServletRequest request, HttpServletResponse response, UserException e) {
        int code = e.getCode();
        if (code== ErrorCode.NOUSER.getCode()) {
            return AjaxResult.error(ResultCode.FAILED.getCode(), "用户不存在");
        } else if (code == ErrorCode.NOAUTH.getCode()) {
            return AjaxResult.unAuth();
        }
        return AjaxResult.unLogin();
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public Object handleNumberFormatException(HttpServletRequest request, HttpServletResponse response, NumberFormatException e) {
        return AjaxResult.error(ResultCode.FAILED.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = PersistenceException.class)
    public Object handlePersistenceException(HttpServletRequest request, HttpServletResponse response, PersistenceException e) {
        return AjaxResult.error(ResultCode.FAILED.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Object handleUsernameNotFoundException(HttpServletRequest request, HttpServletResponse response, UsernameNotFoundException e) {
        return AjaxResult.error(ResultCode.FAILED.getCode(), "用户不存在");
    }
}
