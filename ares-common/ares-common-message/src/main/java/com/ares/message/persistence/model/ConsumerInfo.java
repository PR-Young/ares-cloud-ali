package com.ares.message.persistence.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.persistence.model ConsumerInfo.java
 **/
@Data
public class ConsumerInfo implements Serializable {
    private String queueName;
}
