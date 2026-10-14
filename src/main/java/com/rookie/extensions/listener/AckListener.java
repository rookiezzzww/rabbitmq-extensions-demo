package com.rookie.extensions.listener;

import com.rabbitmq.client.Channel;
import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class AckListener {
    @RabbitListener(queues = Constants.ACK_QUEUE)
    public void handMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //消费者逻辑
            System.out.printf("接收到消息: %s, deliveryTag: %d \n", new String(message.getBody(),"UTF-8"), message.getMessageProperties().getDeliveryTag());

            //进行业务逻辑处理
            System.out.println("业务逻辑处理");
            int num = 3/0;
            System.out.println("业务处理完成");
            //肯定确认
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliveryTag, true, true);
        }
    }

}
