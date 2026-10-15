package com.rookie.extensions.Config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {
    @Bean("rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
    @Bean("confirmRabbitTemplate")
    public RabbitTemplate confirmRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置确认回调方法
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            //CorrelationData 是生产者用来跟踪一条消息的唯一标识对象，配合发布确认（publisher confirm）机制使用。
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("执行了ConfirmCallback\n");
                if (ack) {
                    System.out.printf("接收到消息, 消息ID: %s \n", correlationData==null? null: correlationData.getId());
                } else {
                    System.out.printf("未接收到消息, 消息ID: %s, cause: %s \n", correlationData==null? null: correlationData.getId(), cause);
                    //相应的处理逻辑
                }
            }
        });
        //设置退回回调方法
        rabbitTemplate.setMandatory(true); // 设置mandatory为true，告诉rabbitmq，如果消息无法被路由，则返回给生产者
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(org.springframework.amqp.core.ReturnedMessage returnedMessage) {
                System.out.printf("消息被退回: %s\n", returnedMessage);
            }
        });
        return rabbitTemplate;
    }
    //开启事务
    //1.开启事务管理器
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory){
        return new RabbitTransactionManager(connectionFactory);
    }
    //2.设置事务为true
    @Bean("transRabbitTemplate")
    public RabbitTemplate transRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }
}
