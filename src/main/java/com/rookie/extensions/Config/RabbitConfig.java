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
}
