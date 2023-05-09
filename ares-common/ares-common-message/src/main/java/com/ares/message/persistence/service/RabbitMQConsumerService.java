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

package com.ares.message.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Young 2020/02/25
 **/
@Service
public class RabbitMQConsumerService {

    private Logger logger = LoggerFactory.getLogger(RabbitMQConsumerService.class);


    //@RabbitListener(queues = "ares.queue", containerFactory = "single")
    public void consumer(Message message) {
        try {

            logger.info("监听到消息： {} ", message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
