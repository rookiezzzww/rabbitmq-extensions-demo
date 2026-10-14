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
}
