package com.ares.core.exception;

import com.ares.core.model.base.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/15
 * @see: com.ares.core.common.exception AresCommonException.java
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class AresCommonException extends RuntimeException {

    private static final long serialVersionUID = 6801210771573952361L;
    private Integer errorCode;
    private String message;

    public AresCommonException() {
        super();
    }

    public AresCommonException(String message) {
        super(message);
        this.message = message;
    }

    public AresCommonException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public AresCommonException(ResultCode code) {
        super(code.getMsg());
        this.errorCode = code.getCode();
        this.message = code.getMsg();
    }

    public AresCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public AresCommonException(Throwable cause) {
        super(cause);
    }

}
