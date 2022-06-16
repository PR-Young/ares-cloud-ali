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
