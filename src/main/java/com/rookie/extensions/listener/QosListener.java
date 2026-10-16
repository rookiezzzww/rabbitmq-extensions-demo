package com.rookie.extensions.listener;

import com.rabbitmq.client.Channel;
import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QosListener {
    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void listen(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("消费者1收到消息: " + message + " with delivery tag: " + deliveryTag);
        // 手动确认
        channel.basicAck(deliveryTag, true);
    }
    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void listen2(Message message, Channel channel) throws IOException, InterruptedException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("消费者2收到消息: " + message + " with delivery tag: " + deliveryTag);
        // 手动确认
        Thread.sleep(10);
        channel.basicAck(deliveryTag, true);
    }
}
