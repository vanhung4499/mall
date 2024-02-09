package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.OmsCartItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartPromotionItem extends OmsCartItem {
    @ApiModelProperty("Promotion information")
    private String promotionMessage;
    @ApiModelProperty("The amount reduced by the promotion, for each product")
    private BigDecimal reduceAmount;
    @ApiModelProperty("Remaining stock - locked stock")
    private Integer realStock;
    @ApiModelProperty("Points given when purchasing the product")
    private Integer integration;
    @ApiModelProperty("Growth value given when purchasing the product")
    private Integer growth;
}
