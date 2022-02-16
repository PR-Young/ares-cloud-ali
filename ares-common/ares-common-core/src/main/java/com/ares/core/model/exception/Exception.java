package com.ares.core.model.exception;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/06/19
 **/
@Data
public class Exception<T> {
    private String code;
    private String msg;
    private List<T> errors;

    public static ExceptionBuilder newInstance() {
        return new ExceptionBuilder<>();
    }

    protected Exception(ExceptionBuilder<T> builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.errors = builder.errors;
    }

    public static class ExceptionBuilder<T> {
        private String code;
        private String msg;
        private List<T> errors;

        public ExceptionBuilder() {
        }

        public ExceptionBuilder withCode(String value) {
            this.code = value;
            return this;
        }

        public ExceptionBuilder withMsg(String value) {
            this.msg = value;
            return this;
        }

        public ExceptionBuilder withErrors(List value) {
            this.errors = value;
            return this;
        }

        public Exception build() {
            return new Exception<>(this);
        }
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<T> getErrors() {
        return errors;
    }
}
