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
