package com.hnv99.mall.portal.component;

import com.hnv99.mall.portal.domain.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderSender {
    // Logger for logging
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderSender.class);

    // Autowired AmqpTemplate to send messages to the queue
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes){
        // Send a message to the delay queue
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // Set the delay time for the message in milliseconds
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        // Log the order ID
        LOGGER.info("send orderId:{}",orderId);
    }
}
