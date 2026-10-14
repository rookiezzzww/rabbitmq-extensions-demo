package com.rookie.extensions.controller;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
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
}
