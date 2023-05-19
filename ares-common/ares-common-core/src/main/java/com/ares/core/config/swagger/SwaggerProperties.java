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

package com.ares.core.config.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/15
 * @see: com.ares.config.swagger.SwaggerProperties.java
 **/
@Data
@Component
@ConfigurationProperties(prefix = "ares.swagger")
@RefreshScope
public class SwaggerProperties {

    private boolean enable;

    @Value("${ares.swagger.base.package}")
    private String basePackage;

    @Value("${ares.swagger.contact.name}")
    private String contactName;

    @Value("${ares.swagger.contact.email}")
    private String contactEmail;

    @Value("${ares.swagger.contact.url}")
    private String contactUrl;

    private String description;

    private String title;

    private String url;

    private String version;

    @NestedConfigurationProperty
    private List<ParameterConfig> parameterConfig;

    @Data
    public static class ParameterConfig {
        private String name;
        private String description;
        private String type = "header";
        private String dateType = "String";
        private boolean required;
        private String defaultValue;
    }
}
