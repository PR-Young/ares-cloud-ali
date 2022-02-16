package com.ares.core.model.base;

import lombok.Getter;

/**
 * @description:
 * @author: Young 2020/03/30
 **/
@Getter
public enum ResultCode {

    SUCCESS(200, "成功"),
    FAILED(500, "错误"),
    VALIDATE_FAILED(10001, "参数校验失败"),
    UNKNOWN(10000, "未知错误"),
    NOAUTH(401, "用户没有权限"),
    NOLOGIN(1000, "用户没有登录!"),
    NOMODEL(2000, "模型不存在"),
    NOFLOW(2001, "请先设计流程定义并成功保存，再进行部署"),
    ERRORFLOWDEFINITION(2002, "流程定义不符要求，请至少设计一条主线流程");
    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
