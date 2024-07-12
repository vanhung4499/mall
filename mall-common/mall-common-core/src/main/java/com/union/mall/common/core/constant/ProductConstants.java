package com.union.mall.common.core.constant;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface ProductConstants {

    /**
     * Prefix for the key storing the list of products locked by an order
     */
    String ORDER_LOCKED_SKUS_PREFIX = "order:locked:skus:";

    /**
     * Prefix for the distributed lock key of a product SKU
     */
    String SKU_LOCK_PREFIX = "product:sku:lock:";

    /**
     * Prefix for temporary specification IDs
     */
    String SPEC_TEMP_ID_PREFIX = "tid_";
}
