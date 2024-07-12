package com.union.mall.common.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Configuration
@Slf4j
public class RabbitConfig {

    /**
     * Message serialization configuration.
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // Configure message converter to use Jackson for JSON serialization/deserialization
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        return factory;
    }
}
