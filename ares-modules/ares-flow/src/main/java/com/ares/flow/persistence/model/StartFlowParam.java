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

package com.ares.flow.persistence.model;


import lombok.Data;

import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2026/5/6
 * @see: com.ares.flow.persistence.model.StartFlowParam.java
 **/
@Data
public class StartFlowParam {
    private String flowCode;
    private Map<String, Object> fields;
    private Map<String, Object> data;
    private Map<String, Object> variables;
}
