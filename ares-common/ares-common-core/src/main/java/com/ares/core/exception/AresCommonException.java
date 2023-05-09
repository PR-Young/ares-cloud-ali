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
