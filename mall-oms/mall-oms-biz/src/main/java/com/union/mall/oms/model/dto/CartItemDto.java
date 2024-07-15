package com.union.mall.oms.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class CartItemDto implements Serializable {

    /**
     * Product Stock ID
     */
    private Long skuId;

    /**
     * Quantity of Product
     */
    private Integer count;

    /**
     * Is Selected
     */
    private Boolean checked;

}
