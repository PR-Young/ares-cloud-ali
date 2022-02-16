package com.ares.message.persistence.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.persistence.model ProduceInfo.java
 **/
@Data
public class ProduceInfo implements Serializable {
    private String exchangeName;
    private String routingKey;
    private String msg;
}
