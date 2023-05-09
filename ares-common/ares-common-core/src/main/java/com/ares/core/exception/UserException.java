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

import lombok.Data;

/**
 * @description:
 * @author: Young 2020/05/09
 **/
@Data
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 6109496485495487838L;
    private int code;
    private String message;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
        this.message = message;
    }

    public UserException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public UserException(String message, Throwable e) {
        super(message, e);
    }
}
