package com.union.mall.common.core.constant;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface OrderConstants {

    /**
     * Member shopping cart cache key prefix
     */
    String MEMBER_CART_PREFIX = "MEMBER:CART:";

    /**
     * Order duplicate submission prevention lock key prefix
     */
    String ORDER_RESUBMIT_LOCK_PREFIX = "ORDER:RESUBMIT_LOCK:";

    /**
     * Order lock key prefix
     */
    String ORDER_LOCK_PREFIX = "ORDER:LOCK:";
}
