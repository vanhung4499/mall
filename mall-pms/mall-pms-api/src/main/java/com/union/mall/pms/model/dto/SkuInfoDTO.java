package com.union.mall.pms.model.dto;

import lombok.Data;

@Data
public class SkuInfoDTO {
    /**
     * Unique identifier of the SKU
     */
    private Long id;
    /**
     * SKU code
     */
    private String skuSn;
    /**
     * SKU name
     */
    private String skuName;
    /**
     * SKU image URL
     */
    private String picUrl;
    /**
     * SKU price
     */
    private Long price;
    /**
     * SKU stock quantity
     */
    private Integer stock;
    /**
     * Name of the corresponding SPU
     */
    private String spuName;
}
