package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsMoneyInfoParam {
    @ApiModelProperty("Order ID")
    private Long orderId;
    @ApiModelProperty("Freight amount")
    private BigDecimal freightAmount;
    @ApiModelProperty("Discount amount used by the administrator in the backend")
    private BigDecimal discountAmount;
    @ApiModelProperty("Order status: 0->Pending payment; 1->Awaiting shipment; 2->Shipped; 3->Completed; 4->Closed; 5->Invalid order")
    private Integer status;
}
