package com.hnv99.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class OrderParam {
    @ApiModelProperty("Receiving address ID")
    private Long memberReceiveAddressId;
    @ApiModelProperty("Coupon ID")
    private Long couponId;
    @ApiModelProperty("Number of points used")
    private Integer useIntegration;
    @ApiModelProperty("Payment method")
    private Integer payType;
    @ApiModelProperty("Selected shopping cart product IDs")
    private List<Long> cartIds;
}
