package com.rookie.extensions.Config;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //确认机制
    @Bean("ackQueue")
    public Queue ackQueue() {
        return QueueBuilder.durable(Constants.ACK_QUEUE).build();
    }
    @Bean("ackExchange")
    public Exchange ackExchange() {
        return ExchangeBuilder.topicExchange(Constants.ACK_EXCHANGE_NAME).durable(true).build();
    }
    @Bean("ackBinding")
    public Binding ackBinding(@Qualifier("ackQueue") Queue queue, @Qualifier("ackExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ack").noargs();
    }
    //发送方确认
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(Constants.CONFIRM_QUEUE).build();
    }
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(Constants.CONFIRM_EXCHANGE).build();
    }
    @Bean("confirmBinding")
    public Binding confirmBinding(@Qualifier("confirmQueue") Queue queue, @Qualifier("confirmExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("confirm").noargs();
    }
    //重试机制
    @Bean("retryQueue")
    public Queue retryQueue(){
        return QueueBuilder.durable(Constants.RETRY_QUEUE).build();
    }
    @Bean("retryExchange")
    public Exchange retryExchange(){
        return ExchangeBuilder.fanoutExchange(Constants.RETRY_EXCHANGE_NAME).durable(true).build();
    }
    @Bean("retryBinding")
    public Binding retryBinding(@Qualifier("retryQueue") Queue queue, @Qualifier("retryExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
    //TTL
    @Bean("ttlQueue")
    public Queue ttlQueue(){
        return QueueBuilder.durable(Constants.TTL_QUEUE).build();
    }
    @Bean("ttlQueue2")
    public Queue ttlQueue2(){
        //这里的ttl是队列属性，即队列中所有消息的ttl，不是队列的过期时间
        return QueueBuilder.durable(Constants.TTL_QUEUE2).ttl(20*1000).build();
    }
    @Bean("ttlExchange")
    public Exchange ttlExchange() {
        return ExchangeBuilder.fanoutExchange(Constants.TTL_EXCHANGE_NAME).durable(true).build();
    }
    @Bean("ttlBinding")
    public Binding ttlBinding(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
    @Bean("ttlBinding2")
    public Binding ttlBinding2(@Qualifier("ttlQueue2") Queue queue, @Qualifier("ttlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}
