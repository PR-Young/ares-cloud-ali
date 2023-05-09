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

import java.io.Serializable;

/**
 * @description:
 * @author: Young
 * @date: 2022/6/14
 * @see: com.ares.core.model.base.JsonResult.java
 **/
@Data
public class JsonResult<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    private static <T> JsonResult<T> jsonResult(T data, int code, String msg) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static <T> JsonResult<T> success(T data) {
        return jsonResult(data, ResultCode.SUCCESS.getCode(), null);
    }

    public static <T> JsonResult<T> success(T data, int code, String msg) {
        return jsonResult(data, code, msg);
    }

    public static <T> JsonResult<T> error(T data, String msg) {
        return jsonResult(data, ResultCode.FAILED.getCode(), msg);
    }

    public static <T> JsonResult<T> error(T data, int code, String msg) {
        return jsonResult(data, code, msg);
    }

}
