package com.rookie.extensions.Config;

import com.rookie.extensions.constants.Constants;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransConfig {
    @Bean("transQueue")
    public Queue transQueue() {
        return QueueBuilder.durable(Constants.TRANS_QUEUE).build();
    }
}
