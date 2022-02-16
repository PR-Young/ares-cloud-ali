package com.ares.message.persistence.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.controller QueueConfig.java
 **/
@Data
public class QueueConfig implements Serializable {
    private String exchangeType;
    private String exchangeName;
    private String queueName;
    private String routingKey;
}
