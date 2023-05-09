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

package com.ares.message.common.rocketmq;///*******************************************************************************
// * Copyright (c) 2021 - 9999, ARES
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// ******************************************************************************/
//
//package com.ares.message.common.rocketmq;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.Assert;
//
///**
// * @description:
// * @author: Young
// * @date: 2020/09/29
// * @see: com.ares.message.common.rocketmq RocketMQConfig.java
// **/
//@Configuration
//public class RocketMQConfig {
//
//    @Autowired
//    private RocketMQProperties properties;
//    @Autowired
//    private RocketMsgListener rocketMsgListener;
//
//    @Bean
//    public DefaultMQProducer defaultMQProducer() {
//        Assert.hasText(properties.getProducerGroup(), "[rocketmq.producer.group] must not be null");
//        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(properties.getProducerGroup());
//        defaultMQProducer.setNamesrvAddr(properties.getNameServer());
//        defaultMQProducer.setMaxMessageSize(properties.getMaxMessageSize());
//        defaultMQProducer.setRetryTimesWhenSendFailed(properties.getRetryTimesWhenSendFailed());
//        defaultMQProducer.setVipChannelEnabled(false);
//        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(properties.getRetryTimesWhenSendAsyncFailed());
//        defaultMQProducer.setSendMsgTimeout(properties.getSendMsgTimeout());
//
//        try {
//            defaultMQProducer.start();
//        } catch (MQClientException e) {
//        }
//        return defaultMQProducer;
//    }
//
//    @Bean
//    public DefaultMQPushConsumer defaultMQPushConsumer() {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(properties.getProducerGroup());
//        consumer.setNamesrvAddr(properties.getNameServer());
//        consumer.setConsumeThreadMax(properties.getConsumeThreadMax());
//        consumer.setConsumeThreadMin(properties.getConsumeThreadMin());
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        consumer.setConsumeMessageBatchMaxSize(properties.getConsumeMessageBatchMaxSize());
//        consumer.registerMessageListener(rocketMsgListener);
//
//        try {
//            String[] topics = properties.getTopics().split(";");
//            for (String topicTags : topics) {
//                String[] topicTag = topicTags.split("~");
//                consumer.subscribe(topicTag[0], topicTag[1]);
//            }
//            consumer.start();
//        } catch (Exception e) {
//        }
//        return consumer;
//    }
//}
