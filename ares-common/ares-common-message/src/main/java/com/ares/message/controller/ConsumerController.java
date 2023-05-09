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

package com.ares.message.controller;

import com.ares.core.utils.JsonUtils;
import com.ares.message.persistence.model.ConsumerInfo;
import com.ares.message.utils.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.controller ConsumerController.java
 **/
@Slf4j
@RestController
@RequestMapping("/consumer/*")
public class ConsumerController {
    private SimpleMessageListenerContainer container;
    private RabbitUtil rabbitUtil;

    @Autowired
    public ConsumerController(SimpleMessageListenerContainer container, RabbitUtil rabbitUtil) {
        this.container = container;
        this.rabbitUtil = rabbitUtil;
    }

    @PostMapping("addQueue")
    public void addQueue(@RequestBody ConsumerInfo consumerInfo) {
        boolean existQueue = rabbitUtil.existQueue(consumerInfo.getQueueName());
        if (!existQueue) {
            throw new RuntimeException("当前队列不存在！");
        }
        container.addQueueNames(consumerInfo.getQueueName());
    }

    @PostMapping("removeQueue")
    public void removeQueue(@RequestBody ConsumerInfo consumerInfo) {
        container.removeQueueNames(consumerInfo.getQueueName());
    }

    @GetMapping("getQueue")
    public void getQueue() {
        log.info("container-queue:{}", JsonUtils.toJson(container.getQueueNames()));
    }

}
