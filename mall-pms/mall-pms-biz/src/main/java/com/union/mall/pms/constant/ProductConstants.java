package com.union.mall.pms.constant;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface ProductConstants {

    /**
     * Prefix for cached keys of locked SKUs
     */
    String LOCKED_SKUS_PREFIX = "product:locked_skus:";

    /**
     * Prefix for cached keys of SKU distributed locks
     */
    String SKU_LOCK_PREFIX = "product:sku_lock:";

    /**
     * Prefix for temporary specification IDs
     */
    String SPEC_TEMP_ID_PREFIX = "tid_";

}
