package com.ares.message.common.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Young 2020/02/26
 **/
//@Component
public class KafkaProducer {

    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String message) {
        kafkaTemplate.send("test", message);
    }


}
