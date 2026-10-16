package com.rookie.extensions.Config;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QosConfig {
    @Bean("qosQueue")
    public Queue qosQueue() {
        return QueueBuilder.durable(Constants.QOS_QUEUE).build();
    }
    @Bean("qosExchange")
    public Exchange qosExchange() {
        return ExchangeBuilder.directExchange(Constants.QOS_EXCHANGE_NAME).durable(true).build();
    }
    @Bean
    public Binding qosBinding(@Qualifier("qosQueue") Queue queue, @Qualifier("qosExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("qos").noargs();
    }
}
