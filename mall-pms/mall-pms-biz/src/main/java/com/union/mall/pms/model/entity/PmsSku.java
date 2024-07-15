package com.union.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class PmsSku extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Stock Keeping Unit (SKU) number
     */
    private String skuSn;

    /**
     * SKU name
     */
    private String name;

    /**
     * Standard Product Unit (SPU) ID
     */
    private Long spuId;

    /**
     * Specification IDs, separated by commas (,)
     */
    private String specIds;

    /**
     * Product price (in cents)
     */
    private Long price;

    /**
     * Stock quantity
     */
    private Integer stock;

    /**
     * Locked stock quantity
     */
    private Integer lockedStock;

    /**
     * Product image URL
     */
    private String picUrl;
}
