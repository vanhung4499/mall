package com.union.mall.oms.model.bo;

import com.union.mall.common.core.base.BaseEntity;
import com.union.mall.oms.enums.OrderSourceEnum;
import com.union.mall.oms.enums.OrderStatusEnum;
import com.union.mall.oms.enums.PaymentMethodEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderBO extends BaseEntity {

    /**
     * Order ID
     */
    private Long id;
    /**
     * Order number
     */
    private String orderSn;
    /**
     * Total order amount (in cents)
     */
    private Long totalAmount;
    /**
     * Total quantity of items
     */
    private Integer totalQuantity;

    /**
     * Order source {@link OrderSourceEnum}
     */
    private Integer source;

    /**
     * Order status {@link OrderStatusEnum}
     */
    private Integer status;

    /**
     * Total payable amount (in cents)
     */
    private Long paymentAmount;

    /**
     * Payment method {@link PaymentMethodEnum}
     */
    private Integer paymentMethod;

    /**
     * Order creation time
     */
    private LocalDateTime createTime;

    /**
     * Order remarks
     */
    private String remark;

    /**
     * List of order item details
     */
    private List<OrderItem> orderItems;

    @Data
    public static class OrderItem {

        private Long id;

        /**
         * Order ID
         */
        private Long orderId;

        /**
         * SKU ID
         */
        private Long skuId;

        /**
         * SKU number
         */
        private String skuSn;

        /**
         * Product name
         */
        private String skuName;

        /**
         * Product SKU image
         */
        private String picUrl;

        /**
         * Product unit price (in cents)
         */
        private Long price;

        /**
         * Product quantity
         */
        private Integer quantity;

        /**
         * Total product amount (in cents) (unit price * quantity)
         */
        private Long totalAmount;
    }
}
