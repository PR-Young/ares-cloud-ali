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

package com.ares.generator.persistence.model;


import com.ares.core.model.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description:
 * @author: ares
 * @date: 2025-04-21 10:19:03
 **/
@Data
@Schema(name = "GenProperties对象", description = "")
public class GenProperties extends BaseModel {
    @Schema(description = "")
    private String author;
    @Schema(description = "")
    private String basePackage;
    @Schema(description = "")
    private String controllerPackage;
    @Schema(description = "")
    private String daoPackage;
    @Schema(description = "")
    private String entityPackage;
    @Schema(description = "")
    private String filePrefix;
    @Schema(description = "")
    private String fileSuffix;
    @Schema(description = "")
    private Integer genLevel;
    @Schema(description = "")
    private String servicePackage;
    @Schema(description = "")
    private String tablePrefix;
}
