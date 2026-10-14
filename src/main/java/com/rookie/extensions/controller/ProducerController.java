package com.rookie.extensions.controller;

import com.rookie.extensions.constants.Constants;
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
}
