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

import lombok.Getter;

/**
 * @description:
 * @author: Young
 * @date: 2020/12/18
 * @see: com.ares.core.persistence.model.exception ErrorCode.java
 **/
@Getter
public enum ErrorCode {
    NOUSER(90001, "user is not exist"),
    NOLOGIN(90002, "user is not login"),
    LOGINTIMEOUT(90003, "session time out"),
    NOAUTH(90004, "user has no auth");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
