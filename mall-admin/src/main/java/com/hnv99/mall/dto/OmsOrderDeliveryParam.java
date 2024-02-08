package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsOrderDeliveryParam {
    @ApiModelProperty("Order ID")
    private Long orderId;
    @ApiModelProperty("Delivery company")
    private String deliveryCompany;
    @ApiModelProperty("Delivery number")
    private String deliverySn;
}

