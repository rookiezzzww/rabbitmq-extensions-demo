package com.rookie.extensions.listener;

import com.rabbitmq.client.Channel;
import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class RetryListener {
    @RabbitListener(queues = Constants.RETRY_QUEUE)
    public void listen(Message message, Channel channel) throws IOException {
        System.out.printf("接收到消息: %s, deliveryTag: %d%n", new String(message.getBody(),"UTF-8"), message.getMessageProperties().getDeliveryTag());
        //模拟处理失败
        int num = 3/0;
        //手动确认，如果配置是manual的话；如果是auto，则不需要手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        System.out.println("处理完成");
    }
}
