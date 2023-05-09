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
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @description:
// * @author: Young
// * @date: 2020/10/10
// * @see: com.ares.message.common.rocketmq RocketMsgListener.java
// **/
//@Slf4j
//@Component
//public class RocketMsgListener implements MessageListenerConcurrently {
//    @Autowired
//    private RocketMQProperties properties;
//    private static int RECONSUME = 3;
//
//    @Override
//    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//        if (CollectionUtils.isEmpty(list)) {
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        }
//        MessageExt messageExt = list.get(0);
//        log.info("接受到的消息为：" + new String(messageExt.getBody()));
//        int reConsume = messageExt.getReconsumeTimes();
//        // 消息已经重试了3次，如果不需要再次消费，则返回成功
//        if (reConsume == RECONSUME) {
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        }
//        if (messageExt.getTopic().equals(properties.getPlatTopic())) {
//            String tags = messageExt.getTags();
//            switch (tags) {
//                case "AresAccountTag":
//                    log.info("开户 tag == >>" + tags);
//                    break;
//                default:
//                    log.info("未匹配到Tag == >>" + tags);
//                    break;
//            }
//        }
//        // 消息消费成功
//        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//    }
//}
