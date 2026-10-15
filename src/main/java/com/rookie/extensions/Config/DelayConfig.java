package com.rookie.extensions.Config;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DelayConfig {
    @Bean("delayExchange")
    public Exchange delayExchange() {
        return ExchangeBuilder.directExchange(Constants.DELAY_EXCHANGE_NAME).durable(true).delayed().build();
    }
    @Bean("delayQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable(Constants.DELAY_QUEUE).build();
    }

    @Bean("delayQueueBinding")
    public Binding delayQueueBinding(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("delayed").noargs();
    }

}
