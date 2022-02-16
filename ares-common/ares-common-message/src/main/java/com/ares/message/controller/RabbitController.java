package com.ares.message.controller;

import com.ares.core.model.base.AjaxResult;
import com.ares.message.persistence.model.QueueConfig;
import com.ares.message.utils.RabbitUtil;
import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.ExchangeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.controller RabbitController.java
 **/
@Slf4j
@RestController
@RequestMapping("/config/*")
public class RabbitController {
    private RabbitUtil rabbitUtil;
    private Client client;

    @Autowired
    public RabbitController(RabbitUtil rabbitUtil, Client client) {
        this.rabbitUtil = rabbitUtil;
        this.client = client;
    }

    @PostMapping("addExchange")
    public Object addExchange(@RequestBody QueueConfig config) {
        rabbitUtil.addExchange(config.getExchangeType(), config.getExchangeName());
        return AjaxResult.success();
    }

    @DeleteMapping("deleteExchange")
    public Object deleteExchange(@RequestBody QueueConfig config) {
        rabbitUtil.deleteExchange(config.getExchangeName());
        return AjaxResult.success();
    }

    @PostMapping("addQueue")
    public Object addQueue(@RequestBody QueueConfig config) {
        rabbitUtil.addQueue(config.getQueueName());
        return AjaxResult.success();
    }

    @DeleteMapping("deleteQueue")
    public Object deleteQueue(@RequestBody QueueConfig config) {
        rabbitUtil.deleteQueue(config.getQueueName());
        return AjaxResult.success();
    }

    @PostMapping("purgeQueue")
    public Object purgeQueue(@RequestBody QueueConfig config) {
        rabbitUtil.purgeQueue(config.getQueueName());
        return AjaxResult.success();
    }

    @PostMapping("addBinding")
    public Object addBinding(@RequestBody QueueConfig config) {
        rabbitUtil.addBinding(config.getExchangeType(), config.getExchangeName(), config.getQueueName(), config.getRoutingKey(), false, null);
        return AjaxResult.success();
    }

    @PostMapping("removeBinding")
    public Object removeBinding(@RequestBody QueueConfig config) {
        List<ExchangeInfo> exchangeInfoList = client.getExchanges();
        ExchangeInfo exchangeInfo = exchangeInfoList.stream().filter(info -> config.getExchangeName().equals(info.getName())).findFirst().get();
        config.setExchangeType(exchangeInfo.getType());
        rabbitUtil.removeBinding(config.getExchangeType(), config.getExchangeName(), config.getQueueName(), config.getRoutingKey(), false, null);
        return AjaxResult.success();
    }

    @PostMapping("addExchangeBindingQueueOfHeaderAll")
    public Object addExchangeBindingQueueOfHeaderAll(@RequestBody QueueConfig config) {
        Map<String, Object> header = new HashMap<>(10);
        header.put("queue", "queue");
        header.put("bindType", "whereAll");
        rabbitUtil.andExchangeBindingQueue(RabbitUtil.ExchangeType.HEADERS, config.getExchangeName(), config.getQueueName(), null, true, header);
        return AjaxResult.success();
    }

    @PostMapping("addExchangeBindingQueueOfHeaderAny")
    public Object addExchangeBindingQueueOfHeaderAny(@RequestBody QueueConfig config) {
        Map<String, Object> header = new HashMap<>(10);
        header.put("queue", "queue");
        header.put("bindType", "whereAny");
        rabbitUtil.andExchangeBindingQueue(RabbitUtil.ExchangeType.HEADERS, config.getExchangeName(), config.getQueueName(), null, false, header);
        return AjaxResult.success();
    }

    @GetMapping("getExchanges")
    public Object getExchanges() {
        return AjaxResult.successData(client.getExchanges());
    }

    @GetMapping("getQueues")
    public Object getQueues() {
        return AjaxResult.successData(client.getQueues());
    }

    @GetMapping("getQueueBindings")
    public Object getQueueBindings(@RequestParam("vhost") String vhost,
                                   @RequestParam("queueName") String queueName) {
        return AjaxResult.successData(client.getQueueBindings(vhost, queueName));
    }
}
