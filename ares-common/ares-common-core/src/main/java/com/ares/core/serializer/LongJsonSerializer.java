/*
 *
 *  * ****************************************************************************
 *  * * Copyright (c) 2021 - 9999, ARES
 *  * *
 *  * * Licensed under the Apache License, Version 2.0 (the "License");
 *  * * you may not use this file except in compliance with the License.
 *  * * You may obtain a copy of the License at
 *  * *
 *  * *        http://www.apache.org/licenses/LICENSE-2.0
 *  * *
 *  * * Unless required by applicable law or agreed to in writing, software
 *  * * distributed under the License is distributed on an "AS IS" BASIS,
 *  * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * * See the License for the specific language governing permissions and
 *  * * limitations under the License.
 *  * ***************************************************************************
 *
 */

package com.ares.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @description:
 * @author: Young
 * @date: 2023/8/8
 * @see: com.ares.core.serializer.JsonSerializer.java
 **/
@Slf4j
public class LongJsonSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String text = (value == null ? null : String.valueOf(value));
        if (text != null) {
            jsonGenerator.writeString(text);
        }
    }
}
