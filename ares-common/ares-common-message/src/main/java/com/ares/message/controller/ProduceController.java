package com.ares.message.controller;

import com.ares.message.persistence.model.ProduceInfo;
import com.ares.message.utils.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.controller ProduceController.java
 **/
@Slf4j
@RestController
@RequestMapping("/produce/*")
public class ProduceController {
    private RabbitUtil rabbitUtil;

    @Autowired
    public ProduceController(RabbitUtil rabbitUtil) {
        this.rabbitUtil = rabbitUtil;
    }

    @PostMapping("sendMessage")
    public void sendMessage(@RequestBody ProduceInfo produceInfo) {
        rabbitUtil.convertAndSend(produceInfo.getExchangeName(), produceInfo.getRoutingKey(), produceInfo.getMsg());
    }
}
