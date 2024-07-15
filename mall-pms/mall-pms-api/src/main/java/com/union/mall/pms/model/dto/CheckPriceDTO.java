package com.union.mall.pms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckPriceDTO {

    /**
     * Total order amount
     */
    private Long totalAmount;

    /**
     * Order item details
     */
    private List<OrderSku> skus;

    /**
     * Order item object
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderSku {
        /**
         * Product ID
         */
        private Long skuId;

        /**
         * Quantity of the product
         */
        private Integer count;
    }
}
