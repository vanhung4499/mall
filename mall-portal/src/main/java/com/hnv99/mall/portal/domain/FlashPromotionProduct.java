package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.PmsProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FlashPromotionProduct extends PmsProduct {
    @ApiModelProperty("Flash sale price")
    private BigDecimal flashPromotionPrice;
    @ApiModelProperty("Flash sale count")
    private Integer flashPromotionCount;
    @ApiModelProperty("Flash sale limit quantity")
    private Integer flashPromotionLimit;
}
