package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsReceiverInfoParam {
    @ApiModelProperty(value = "Order ID")
    private Long orderId;
    @ApiModelProperty(value = "Receiver name")
    private String receiverName;
    @ApiModelProperty(value = "Receiver phone")
    private String receiverPhone;
    @ApiModelProperty(value = "Receiver postal code")
    private String receiverPostCode;
    @ApiModelProperty(value = "Detailed address")
    private String receiverDetailAddress;
    @ApiModelProperty(value = "Province/direct-controlled municipality")
    private String receiverProvince;
    @ApiModelProperty(value = "City")
    private String receiverCity;
    @ApiModelProperty(value = "District")
    private String receiverRegion;
    @ApiModelProperty(value = "Order status: 0->Pending payment; 1->Awaiting shipment; 2->Shipped; 3->Completed; 4->Closed; 5->Invalid order")
    private Integer status;
}
