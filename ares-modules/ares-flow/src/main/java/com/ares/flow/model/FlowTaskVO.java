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

package com.ares.flow.model;


import lombok.Data;
import org.dromara.warm.flow.core.dto.FlowParams;

import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2026/5/6
 * @see: com.ares.flow.model.FlowTaskVO.java
 **/
@Data
public class FlowTaskVO {
    Long instanceId;
    Long taskId;
    String message;
    Map<String, Object> variable;
    FlowParams params;
}
