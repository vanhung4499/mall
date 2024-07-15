package com.union.mall.oms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OmsOrderItem extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Order ID
     */
    private Long orderId;

    /**
     * Product Name
     */
    private String spuName;

    /**
     * Specification ID
     */
    private Long skuId;

    /**
     * SKU Code
     */
    private String skuSn;

    /**
     * Specification Name
     */
    private String skuName;

    /**
     * Product SKU Image URL
     */
    private String picUrl;

    /**
     * Unit Price of the Product (in cents)
     */
    private Long price;

    /**
     * Quantity of the Product
     */
    private Integer quantity;

    /**
     * Total Amount of the Product (in cents)
     */
    private Long totalAmount;

    /**
     * Logical Deletion (0: Normal; 1: Deleted)
     */
    private Integer deleted;

}

