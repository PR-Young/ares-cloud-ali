package com.ares.message.common.rocketmq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/29
 * @see: com.ares.message.common.rocketmq RocketMQProperties.java
 **/
@ConfigurationProperties(prefix = "rocketmq")
@Configuration
@Setter
@Getter
public class RocketMQProperties {

    @Value("${rocketmq.name-server}")
    private String nameServer;
    @Value("${rocketmq.producer.group}")
    private String producerGroup;
    @Value("${rocketmq.producer.max-message-size}")
    private int maxMessageSize;
    @Value("${rocketmq.producer.send-message-timeout}")
    private int sendMsgTimeout;
    @Value("${rocketmq.producer.retry-times-when-send-failed}")
    private int retryTimesWhenSendFailed;
    @Value("${rocketmq.producer.retry-times-when-send-async-failed}")
    private int retryTimesWhenSendAsyncFailed;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Value("${rocketmq.ares-plat.ares-plat-group}")
    private String platGroup;
    @Value("${rocketmq.ares-plat.ares-plat-topic}")
    private String platTopic;
    @Value("${rocketmq.ares-plat.ares-account-tag}")
    private String accountTag;
    @Value("${rocketmq.ares-plat.ares-key}")
    private String key;

}
