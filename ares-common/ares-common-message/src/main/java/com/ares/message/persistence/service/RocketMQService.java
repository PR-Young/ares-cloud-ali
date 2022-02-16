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
