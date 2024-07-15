package com.union.mall.oms.model.dto;

import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class OrderItemDTO {

    /**
     * Product Stock Keeping Unit ID
     */
    private Long skuId;

    /**
     * SKU Code
     */
    private String skuSn;

    /**
     * SKU Name
     */
    private String skuName;

    /**
     * Product Image URL
     */
    private String picUrl;

    /**
     * Product Price
     */
    private Long price;

    /**
     * Product Name
     */
    private String spuName;

    /**
     * Order Item Quantity
     */
    private Integer quantity;
}
