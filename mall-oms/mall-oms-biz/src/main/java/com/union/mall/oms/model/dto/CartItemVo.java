package com.union.mall.oms.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class CartItemVo implements Serializable {

    /**
     * Product Stock ID
     */
    private Long skuId;

    /**
     * Product Name
     */
    private String spuName;

    /**
     * Specification Set
     */
    private Set<String> specs;

    /**
     * Product Image URL
     */
    private String imageUrl;

    /**
     * Quantity Added to Cart
     */
    private Integer count;

    /**
     * Product Price
     */
    private Long price;

    /**
     * Is Selected
     */
    private Boolean checked;

    /**
     * Product Stock
     */
    private Integer stock;

}
