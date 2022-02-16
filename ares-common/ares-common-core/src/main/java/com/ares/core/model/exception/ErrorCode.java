package com.ares.core.model.exception;

import lombok.Getter;

/**
 * @description:
 * @author: Young
 * @date: 2020/12/18
 * @see: com.ares.core.persistence.model.exception ErrorCode.java
 **/
@Getter
public enum ErrorCode {
    NOUSER("90001", "user is not exist"),
    NOLOGIN("90002", "user is not login"),
    LOGINTIMEOUT("90003", "session time out"),
    NOAUTH("90004", "user has no auth");

    private String code;
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
