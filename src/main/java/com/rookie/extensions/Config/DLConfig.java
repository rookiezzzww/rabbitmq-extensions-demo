package com.rookie.extensions.Config;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DLConfig {
    //死信交换机
    @Bean("dlxExchange")
    public Exchange dlxExchange(){
        return ExchangeBuilder.topicExchange(Constants.DLX_EXCHANGE_NAME).durable(true).build();
    }
    //2. 死信队列
    @Bean("dlxQueue")
    public Queue dlxQueue() {
        return QueueBuilder.durable(Constants.DLX_QUEUE).build();
    }
    //3. 死信队列和交换机绑定 Binding
    @Bean("dlxBinding")
    public Binding dlxBinding(@Qualifier("dlxExchange") Exchange exchange, @Qualifier("dlxQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("dlx").noargs();
    }
    //正常交换机
    @Bean("normalExchange")
    public Exchange normalExchange(){
        return ExchangeBuilder.topicExchange(Constants.NORMAL_EXCHANGE_NAME).durable(true).build();
    }
    //正常队列
//    @Bean("normalQueue1")
//    public Queue normalQueue() {
//        return QueueBuilder.durable(Constants.NORMAL_QUEUE).build();
//    }
    //演示死信队列的使用
    @Bean("normalQueue2")
    public Queue normalQueue2() {
        return QueueBuilder.durable(Constants.NORMAL_QUEUE)
                .deadLetterExchange(Constants.DLX_EXCHANGE_NAME)
                .deadLetterRoutingKey("dlx")
                .ttl(10000)
                .maxLength(10)
                .build();
    }

    //正常队列和交换机绑定 Binding
    @Bean("normalBinding")
    public Binding normalBinding(@Qualifier("normalExchange") Exchange exchange, @Qualifier("normalQueue2") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("normal").noargs();
    }


}
