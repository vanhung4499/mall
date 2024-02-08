package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsOrderQueryParam {
    @ApiModelProperty(value = "Order number")
    private String orderSn;
    @ApiModelProperty(value = "Receiver name/number")
    private String receiverKeyword;
    @ApiModelProperty(value = "Order status: 0->Pending payment; 1->Awaiting shipment; 2->Shipped; 3->Completed; 4->Closed; 5->Invalid order")
    private Integer status;
    @ApiModelProperty(value = "Order type: 0->Normal order; 1->Flash sale order")
    private Integer orderType;
    @ApiModelProperty(value = "Order source: 0->PC order; 1->app order")
    private Integer sourceType;
    @ApiModelProperty(value = "Order submission time")
    private String createTime;
}
