/*
 *
 *  * !******************************************************************************
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

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @description:
 * @author: Young
 * @date: 2023/8/1
 * @see: com.ares.config.swagger.SwaggerOpenApiConfig.java
 **/
@Configuration
public class SwaggerOpenApiConfig {

    @Resource
    SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact()
                .name(swaggerProperties.getContactName())
                .email(swaggerProperties.getContactEmail())
                .url(swaggerProperties.getContactUrl())
                .extensions(new HashMap<>());

        License license = new License()
                .name("MIT");

        Info info = new Info()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(license)
                .contact(contact);

        return new OpenAPI().openapi("3.0.1")
                .info(info);
    }
}
