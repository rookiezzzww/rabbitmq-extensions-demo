package com.rookie.extensions.listener;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class DelayListener {
    @RabbitListener(queues = Constants.DELAY_QUEUE)
    public void listen(Message message) throws UnsupportedEncodingException {
        System.out.printf("%tc 死信队列接收到消息: %s%n", new Date(), new String(message.getBody(),"UTF-8"));
    }
}
