package com.rookie.extensions.controller;

import com.rookie.extensions.constants.Constants;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "confirmRabbitTemplate")
    private RabbitTemplate confirmRabbitTemplate; // 确认机制

    @RequestMapping("/ack")
    public String test() {
        String msg = "Hello, ack!";
        rabbitTemplate.convertAndSend(Constants.ACK_EXCHANGE_NAME, "ack", msg);
        return "发送成功";
    }
    @RequestMapping("/pres")
    public String testPres() {
        String msg = "消息非持久化测试";
        //创建消息并设置为非持久化
        Message message = new Message(msg.getBytes(), new MessageProperties());
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        //发送消息
        rabbitTemplate.convertAndSend(Constants.ACK_EXCHANGE_NAME, "ack", message);
        return "发送成功";
    }
    //发送方确认
    @RequestMapping("/confirm")
    public String testConfirm() {
        String msg = "Hello, confirm!";
        CorrelationData correlationData = new CorrelationData("1");
        confirmRabbitTemplate.convertAndSend(Constants.CONFIRM_EXCHANGE, "confirm", msg, correlationData);
        return "发送成功";
    }
    @RequestMapping("/return")
    public String testReturn() {
        String msg = "Hello, return!";
        CorrelationData correlationData = new CorrelationData("2");
        confirmRabbitTemplate.convertAndSend(Constants.CONFIRM_EXCHANGE, "wrongRoutingKey", msg, correlationData);
        return "发送成功";
    }
    @RequestMapping("/retry")
    public String testRetry() {
        String msg = "Hello, retry!";
        rabbitTemplate.convertAndSend(Constants.RETRY_EXCHANGE_NAME, "", msg);
        return "发送成功";
    }
    @RequestMapping("/ttl")
    //用messagePostProcessor自动走转换器，如果是对象能自动转 JSON，推荐。
    // 不推荐ttl2方法，因为走的是默认的转换器，如果是对象，就麻烦了
    public String testTTL() {
        String msg = "Hello, TTL!";
        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE_NAME, "", msg, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setExpiration("10000");
            return messagePostProcessor;
        });
        return "发送成功";
    }
//    @RequestMapping("/ttl2")
//    public String testTTL2() {
//        String msg = "Hello, TTL!";
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setExpiration("10000");
//        Message message = new Message(msg.getBytes(), messageProperties);
//        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE_NAME, "", message);
//        return "发送成功";
//    }
    @RequestMapping("/ttlQueue2")
    public String testTTLQueue() {
        String msg = "Hello, TTL Queue2!";
        //发送不带ttl的消息，那么消息的ttl就是队列的ttl
        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE_NAME, "", msg);
        //发送带ttl的消息，那么消息的ttl就是队列的ttl和消息的ttl的最小值
//        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE_NAME, "", msg, messagePostProcessor -> {
//            messagePostProcessor.getMessageProperties().setExpiration("10000");
//            return messagePostProcessor;
//        });
        return "发送成功";
    }
    //测试死信队列
    @RequestMapping("/dlx")
    public String testDLX() {
        String msg = "Hello, DLX!";
        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE_NAME, "normal", msg);
        //测试队列⻓度
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE_NAME, "normal", msg + i);
//        }
        return "发送成功";
    }
    //delay
    @RequestMapping("/delay")
    public String testDelay() {
        String msg = "Hello, Delay!";
        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE_NAME, "normal", msg +" 10s "+ new Date(), messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setExpiration("10000");
            return messagePostProcessor;
        });
        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE_NAME, "normal", msg +" 15s "+ new Date(), messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setExpiration("15000");
            return messagePostProcessor;
        });
        return "发送成功";
    }

    /**
     * 测试延迟队列插件，这时用的是setDelayLong,而非setExpiration
     * @return
     */
    @RequestMapping("/delay2")
    public String testDelay2() {
        String msg = "Hello, Delay2!";
        rabbitTemplate.convertAndSend(Constants.DELAY_EXCHANGE_NAME, "delayed", msg +" 20s "+ new Date(), messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setDelayLong(20000L);
            return messagePostProcessor;
        });
        rabbitTemplate.convertAndSend(Constants.DELAY_EXCHANGE_NAME, "delayed", msg +" 10s "+ new Date(), messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setDelayLong(10000L);
            return messagePostProcessor;
        });
        return "发送成功";
    }
}
