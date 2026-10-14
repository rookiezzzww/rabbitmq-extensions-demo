package com.rookie.extensions.listener;

import com.rabbitmq.client.Channel;
import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DLListener {
    /**
     * 正常队列如果消费失败，比如消费异常，并且设置了不重新入队，那么消息也将被发送到死信队列
     *
     */
//    @RabbitListener(queues = Constants.NORMAL_QUEUE)
//    public void handMessage(Message message, Channel channel) throws Exception {
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        try {
//            //消费者逻辑
//            System.out.printf("[normal.queue]接收到消息: %s, deliveryTag: %d \n", new String(message.getBody(),"UTF-8"), message.getMessageProperties().getDeliveryTag());
//
//            //进行业务逻辑处理
//            System.out.println("业务逻辑处理");
//            int num = 3/0;
//            System.out.println("业务处理完成");
//            //肯定确认
//            channel.basicAck(deliveryTag,false);
//        } catch (Exception e) {
//            //否定确认
//            channel.basicNack(deliveryTag, false, false);  //requeue为false, 该消息成为死信
//        }
//    }
    @RabbitListener(queues = Constants.DLX_QUEUE)
    public void dlHandMessage(Message message, Channel channel) throws Exception {
        //消费者逻辑
        System.out.printf("[dl.queue] %tc 接收到消息: %s, deliveryTag: %d \n", new Date(), new String(message.getBody(),"UTF-8"), message.getMessageProperties().getDeliveryTag());

    }
}
