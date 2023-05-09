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

package com.ares.core.model.base;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @description:
 * @author: Young 2020/01/23
 **/
@Data
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = -8900024560438106316L;

    public AjaxResult() {
    }

    public static AjaxResult error(int code, String message) {
        AjaxResult result = new AjaxResult();
        result.put("code", code);
        result.put("msg", message);
        return result;
    }

    public static AjaxResult success(int code, String message) {
        AjaxResult result = new AjaxResult();
        result.put("code", code);
        result.put("msg", message);
        return result;
    }

    public static AjaxResult error() {
        return error(ResultCode.FAILED.getCode(), "操作失败");
    }

    public static AjaxResult error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static AjaxResult success(String msg) {
        AjaxResult result = new AjaxResult();
        result.put("msg", msg);
        result.put("code", HttpStatus.OK.value());
        return result;
    }

    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static AjaxResult successData(int code, Object value) {
        AjaxResult result = new AjaxResult();
        result.put("code", code);
        result.put("data", value);
        return result;
    }

    public static AjaxResult successData(Object value) {
        AjaxResult result = new AjaxResult();
        result.put("code", HttpStatus.OK.value());
        result.put("data", value);
        return result;
    }

    public static AjaxResult result(int code, String msg, Object value) {
        AjaxResult result = new AjaxResult();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", value);
        return result;
    }

    public static AjaxResult unAuth() {
        AjaxResult result = new AjaxResult();
        result.put("code", ResultCode.NOAUTH.getCode());
        result.put("msg", ResultCode.NOAUTH.getMsg());
        return result;
    }

    public static AjaxResult unLogin() {
        AjaxResult result = new AjaxResult();
        result.put("code", ResultCode.NOLOGIN.getCode());
        result.put("msg", ResultCode.NOLOGIN.getMsg());
        return result;
    }
}
