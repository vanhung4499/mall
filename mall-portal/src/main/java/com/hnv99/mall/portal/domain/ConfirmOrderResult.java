package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.UmsIntegrationConsumeSetting;
import com.hnv99.mall.model.UmsMemberReceiveAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ConfirmOrderResult {
    @ApiModelProperty("Shopping cart information that includes promotional information")
    private List<CartPromotionItem> cartPromotionItemList;
    @ApiModelProperty("User's delivery address list")
    private List<UmsMemberReceiveAddress> memberReceiveAddressList;
    @ApiModelProperty("Available coupon list for the user")
    private List<SmsCouponHistoryDetail> couponHistoryDetailList;
    @ApiModelProperty("Rules for using points")
    private UmsIntegrationConsumeSetting integrationConsumeSetting;
    @ApiModelProperty("Points held by the member")
    private Integer memberIntegration;
    @ApiModelProperty("Calculated amount")
    private CalcAmount calcAmount;

    @Getter
    @Setter
    public static class CalcAmount{
        @ApiModelProperty("Total amount of order items")
        private BigDecimal totalAmount;
        @ApiModelProperty("Freight cost")
        private BigDecimal freightAmount;
        @ApiModelProperty("Promotional discount")
        private BigDecimal promotionAmount;
        @ApiModelProperty("Payable amount")
        private BigDecimal payAmount;
    }
}

