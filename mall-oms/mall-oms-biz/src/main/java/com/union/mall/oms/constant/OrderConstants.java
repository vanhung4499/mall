package com.union.mall.oms.constant;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface OrderConstants {

    /**
     * Cache key prefix for member shopping cart
     */
    String MEMBER_CART_PREFIX = "order:cart:";

    /**
     * Cache key prefix for order anti-duplicate submission token
     */
    String ORDER_TOKEN_PREFIX = "order:token:";

    /**
     * Cache key prefix for order lock
     */
    String ORDER_LOCK_PREFIX = "order:lock";

}
