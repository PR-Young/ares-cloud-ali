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
