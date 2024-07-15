package com.union.mall.pms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockSkuDTO {

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 商品数量
     */
    private Integer quantity;


}
