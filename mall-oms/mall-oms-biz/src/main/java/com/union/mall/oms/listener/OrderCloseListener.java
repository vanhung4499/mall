package com.union.mall.oms.listener;

import com.rabbitmq.client.Channel;
import com.union.mall.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCloseListener {
    private final OrderService orderService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "order.close.queue")
    public void closeOrder(String orderSn, Message message, Channel channel) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // Message sequence number (position in the message queue)

        log.info("Order ({}) not paid on time, system automatically closes the order", orderSn);
        try {
            boolean closeOrderResult = orderService.closeOrder(orderSn);
            log.info("Order close result: {}", closeOrderResult);
            if (closeOrderResult) {
                // Order closed successfully: release inventory
                rabbitTemplate.convertAndSend("stock.exchange", "stock.unlock", orderSn);
            } else {
                // Order close failed: the order has already been closed, manually ACK and remove the message from the queue
                channel.basicAck(deliveryTag, false); // false: do not batch acknowledge, only acknowledge the current single message
            }
        } catch (Exception e) {
            // Order close exception: reject the message and requeue
            try {
                channel.basicReject(deliveryTag, true); // true: requeue the message
                // channel.basicReject(deliveryTag, false); // false: discard the message directly (TODO scheduled task compensation)
            } catch (IOException ex) {
                log.error("Failed to close order ({}), reason: {}", orderSn, ex.getMessage());
            }

        }
    }
}

