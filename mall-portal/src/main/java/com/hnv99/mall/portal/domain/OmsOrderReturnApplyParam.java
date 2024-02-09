package com.hnv99.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsOrderReturnApplyParam {
    @ApiModelProperty("Order ID")
    private Long orderId;
    @ApiModelProperty("Return product ID")
    private Long productId;
    @ApiModelProperty("Order number")
    private String orderSn;
    @ApiModelProperty("Member username")
    private String memberUsername;
    @ApiModelProperty("Returner's name")
    private String returnName;
    @ApiModelProperty("Returner's phone")
    private String returnPhone;
    @ApiModelProperty("Product picture")
    private String productPic;
    @ApiModelProperty("Product name")
    private String productName;
    @ApiModelProperty("Product brand")
    private String productBrand;
    @ApiModelProperty("Product sales attributes: color: red; size: xl;")
    private String productAttr;
    @ApiModelProperty("Return quantity")
    private Integer productCount;
    @ApiModelProperty("Product unit price")
    private BigDecimal productPrice;
    @ApiModelProperty("Product actual paid unit price")
    private BigDecimal productRealPrice;
    @ApiModelProperty("Reason")
    private String reason;
    @ApiModelProperty("Description")
    private String description;
    @ApiModelProperty("Proof pictures, separated by commas")
    private String proofPics;
}
