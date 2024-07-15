package com.union.mall.pms.model.vo;

import com.union.mall.common.core.base.BaseVO;
import lombok.*;

/**
 * Order item
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderItemVO extends BaseVO {

    /**
     * Product ID
     */
    @Getter
    private Long skuId;

    /**
     * Product image
     */
    @Getter
    private String skuImg;

    /**
     * Product name
     */
    @Getter
    private String title;

    /**
     * Quantity of the product
     */
    @Getter
    private Integer number;

    /**
     * Unit price of the product
     */
    @Getter
    private Long price;

    @Getter
    private Long coupon = 0L;

    /**
     * Subtotal
     */
    private Long subTotal;

    /**
     * Calculate subtotal based on price and number of items
     */
    public Long getSubTotal() {
        long total = 0L;
        if (price != null && number != null){
            total = price * number;
        }
        return total;
    }
}
