package com.ares.message.utils;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/08
 * @see: com.ares.message.utils RabbitUtil.java
 **/
public class RabbitUtil {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建exchange
     *
     * @param exchangeType
     * @param exchangeName
     */
    public void addExchange(String exchangeType, String exchangeName) {
        Exchange exchange = createExchange(exchangeType, exchangeName);
        rabbitAdmin.declareExchange(exchange);
    }

    /**
     * 删除exchange
     *
     * @param exchangeName
     * @return
     */
    public boolean deleteExchange(String exchangeName) {
        return rabbitAdmin.deleteExchange(exchangeName);
    }

    /**
     * 创建一个指定的queue
     *
     * @param queueName
     */
    public void addQueue(String queueName) {
        Queue queue = createQueue(queueName);
        rabbitAdmin.declareQueue(queue);
    }

    /**
     * 删除queue
     *
     * @param queueName
     * @return
     */
    public boolean deleteQueue(String queueName) {
        return rabbitAdmin.deleteQueue(queueName);
    }

    /**
     * 按照筛选条件删除队列
     *
     * @param queueName
     * @param unused
     * @param empty
     */
    public void deleteQueue(String queueName, boolean unused, boolean empty) {
        rabbitAdmin.deleteQueue(queueName, unused, empty);
    }

    /**
     * 清空队列消息
     *
     * @param queueName
     */
    public void purgeQueue(String queueName) {
        rabbitAdmin.purgeQueue(queueName, false);
    }

    /**
     * 判断队列是否存在
     *
     * @param queueName
     * @return
     */
    public boolean existQueue(String queueName) {
        return rabbitAdmin.getQueueProperties(queueName) == null ? false : true;
    }

    /**
     * 绑定一个队列到一个匹配型交换器使用一个routingkey
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     */
    public void addBinding(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        Binding binding = bindingBuilder(exchangeType, exchangeName, queueName, routingKey, isWhereAll, headers);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 声明绑定
     *
     * @param binding
     */
    public void addBinding(Binding binding) {
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 解除交换器与队列的绑定
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     */
    public void removeBinding(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        Binding binding = bindingBuilder(exchangeType, exchangeName, queueName, routingKey, isWhereAll, headers);
        rabbitAdmin.removeBinding(binding);
    }

    /**
     * 解除交换器与队列的绑定
     *
     * @param binding
     */
    public void removeBinding(Binding binding) {
        rabbitAdmin.removeBinding(binding);
    }

    /**
     * 创建一个交换器、队列，并绑定队列
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     */
    public void andExchangeBindingQueue(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        addExchange(exchangeType, exchangeName);
        addQueue(queueName);
        addBinding(exchangeType, exchangeName, queueName, routingKey, isWhereAll, headers);
    }

    /**
     * 发送消息
     *
     * @param exchange
     * @param routingKey
     * @param object
     */
    public void convertAndSend(String exchange, String routingKey, Object object) {
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

    /**
     * 转换message对象
     *
     * @param messageType
     * @param msg
     * @return
     */
    public Message getMessage(String messageType, Object msg) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(messageType);
        Message message = new Message(msg.toString().getBytes(), messageProperties);
        return message;
    }

    /**
     * 声明交换机
     *
     * @param exchangeType
     * @param exchangeName
     * @return
     */
    private Exchange createExchange(String exchangeType, String exchangeName) {
        if (ExchangeType.DIRECT.equalsIgnoreCase(exchangeType)) {
            return new DirectExchange(exchangeName);
        }
        if (ExchangeType.TOPIC.equalsIgnoreCase(exchangeType)) {
            return new TopicExchange(exchangeName);
        }
        if (ExchangeType.HEADERS.equalsIgnoreCase(exchangeType)) {
            return new HeadersExchange(exchangeName);
        }
        if (ExchangeType.FANOUT.equalsIgnoreCase(exchangeType)) {
            return new FanoutExchange(exchangeName);
        }
        return null;
    }

    /**
     * 声明绑定关系
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     * @return
     */
    private Binding bindingBuilder(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        if (ExchangeType.DIRECT.equalsIgnoreCase(exchangeType)) {
            return BindingBuilder.bind(new Queue(queueName)).to(new DirectExchange(exchangeName)).with(routingKey);
        }
        if (ExchangeType.TOPIC.equalsIgnoreCase(exchangeType)) {
            return BindingBuilder.bind(new Queue(queueName)).to(new TopicExchange(exchangeName)).with(routingKey);
        }
        if (ExchangeType.HEADERS.equalsIgnoreCase(exchangeType)) {
            if (isWhereAll) {
                return BindingBuilder.bind(new Queue(queueName)).to(new HeadersExchange(exchangeName)).whereAll(headers).match();
            } else {
                return BindingBuilder.bind(new Queue(queueName)).to(new HeadersExchange(exchangeName)).whereAny(headers).match();
            }
        }
        if (ExchangeType.FANOUT.equalsIgnoreCase(exchangeType)) {
            return BindingBuilder.bind(new Queue(queueName)).to(new FanoutExchange(exchangeName));
        }
        return null;
    }

    /**
     * 声明队列
     *
     * @param queueName
     * @return
     */
    private Queue createQueue(String queueName) {
        return new Queue(queueName);
    }

    /**
     * 交换器类型
     */
    public final static class ExchangeType {
        /**
         * 直连交换机（全文匹配）
         */
        public final static String DIRECT = "DIRECT";
        /**
         * 通配符交换机（*只能匹配一个单词，#可以匹配零个或多个）
         */
        public final static String TOPIC = "TOPIC";
        /**
         * 头交换机（自定义键值对匹配，根据发送消息内容中的headers属性匹配）
         */
        public final static String HEADERS = "HEADERS";
        /**
         * 扇形（广播）交换机（将消息转发到所有与该交互机绑定的队列上）
         */
        public final static String FANOUT = "FANOUT";
    }
}
