package com.union.mall.pms.listener;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
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
public class StockReleaseListener {

    private final SkuService skuService;

    private static final String STOCK_RELEASE_QUEUE = "stock.release.queue";
    private static final String STOCK_EXCHANGE = "stock.exchange";
    private static final String STOCK_RELEASE_ROUTING_KEY = "stock.release.routing.key";

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = STOCK_RELEASE_QUEUE, durable = "true"),
            exchange = @Exchange(value = STOCK_EXCHANGE),
            key = {STOCK_RELEASE_ROUTING_KEY}
    ),
            ackMode = "MANUAL" // Manual ACK
    )
    @RabbitHandler
    public void handleStockRelease(String orderSn, Message message, Channel channel) throws IOException {
        log.info("Listening for order {} cancellation to release stock", orderSn);
        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // Message tag
        try {
            skuService.unlockStock(orderSn);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, true);
        }
    }
}
