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

package com.ares.message.persistence.service;//package com.ares.message.persistence.service;
//
//import com.ares.message.common.rocketmq.RocketMQProperties;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @description:
// * @author: Young
// * @date: 2020/10/10
// * @see: com.ares.message.persistence.service RocketMQService.java
// **/
//@Service
//public class RocketMQService {
//
//    @Autowired
//    private RocketMQProperties properties;
//    @Autowired
//    private DefaultMQProducer defaultMQProducer;
//
//    public SendResult send(String msg) {
//        SendResult sendResult = new SendResult();
//        try {
//            Message message = new Message(properties.getPlatTopic(), properties.getAccountTag(), properties.getKey(), msg.getBytes());
//            sendResult = defaultMQProducer.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sendResult;
//    }
//}
