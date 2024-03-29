package com.hnv99.mall.portal.domain;

import lombok.Getter;

@Getter
public enum QueueEnum {
    /**
     * Message notification queue
     */
    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    /**
     * Message notification TTL queue
     */
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");

    /**
     * Exchange name
     */
    private final String exchange;
    /**
     * Queue name
     */
    private final String name;
    /**
     * Routing key
     */
    private final String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
