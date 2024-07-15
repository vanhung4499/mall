package com.union.mall.oms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Order close delayed queue configuration.
 *
 * @author vanhung4499
 */
@Component
@Slf4j
public class OrderCloseRabbitConfig {

    // Regular delayed queue
    private static final String ORDER_CLOSE_DELAY_QUEUE = "order.close.delay.queue";
    private static final String ORDER_EXCHANGE = "order.exchange";
    private static final String ORDER_CLOSE_DELAY_ROUTING_KEY = "order.close.delay.routing.key";

    // Dead-letter order close queue
    private static final String ORDER_CLOSE_QUEUE = "order.close.queue";
    private static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    private static final String ORDER_CLOSE_ROUTING_KEY = "order.close.routing.key";

    /**
     * Define the main exchange.
     */
    @Bean
    public Exchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    /**
     * Define the dead-letter exchange.
     */
    @Bean
    public Exchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE, true, false);
    }

    /**
     * Delay queue definition.
     */
    @Bean
    public Queue orderDelayQueue() {
        // Messages in the delay queue will expire and be routed to the dead-letter exchange
        // with the specified routing key.
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", ORDER_CLOSE_ROUTING_KEY); // Dead-letter routing key
        args.put("x-message-ttl", 5 * 1000L); // 5 seconds for testing purposes
        return new Queue(ORDER_CLOSE_DELAY_QUEUE, true, false, false, args);
    }

    /**
     * Bind the delay queue to the main exchange.
     */
    @Bean
    public Binding orderDelayQueueBinding() {
        return new Binding(ORDER_CLOSE_DELAY_QUEUE, Binding.DestinationType.QUEUE, ORDER_EXCHANGE,
                ORDER_CLOSE_DELAY_ROUTING_KEY, null);
    }

    /**
     * Define the dead-letter queue.
     */
    @Bean
    public Queue orderCloseQueue() {
        log.info("Dead-letter queue (order.close.queue) created");
        return new Queue(ORDER_CLOSE_QUEUE, true, false, false);
    }

    /**
     * Bind the dead-letter queue to the dead-letter exchange.
     */
    @Bean
    public Binding orderCloseQueueBinding() {
        return new Binding(ORDER_CLOSE_QUEUE, Binding.DestinationType.QUEUE, ORDER_DLX_EXCHANGE,
                ORDER_CLOSE_ROUTING_KEY, null);
    }

}
