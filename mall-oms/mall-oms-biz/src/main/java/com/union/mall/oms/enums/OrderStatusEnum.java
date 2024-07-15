package com.union.mall.oms.enums;

import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Getter
public enum OrderStatusEnum implements IBaseEnum<Integer> {

    /**
     * Unpaid
     */
    UNPAID(0, "Unpaid"),
    /**
     * Paid (Awaiting Shipment)
     */
    PAID(1, "Paid"),
    /**
     * Shipped
     */
    SHIPPED(2, "Shipped"),
    /**
     * Complete
     */
    COMPLETE(3, "Complete"),
    /**
     * Canceled
     */
    CANCELED(4, "Canceled"),
    /**
     * In After-Sales Service
     */
    SERVICING(5, "In Service");

    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private Integer value;

    private String label;

}
