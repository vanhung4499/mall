package com.hnv99.mall.portal.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AliPayParam {
    /**
     * Merchant order number, customized by the merchant, must be unique
     */
    private String outTradeNo;
    /**
     * Title of the product / transaction / order / order keywords, etc.
     */
    private String subject;
    /**
     * Total order amount, in yuan, accurate to two decimal places
     */
    private BigDecimal totalAmount;
}

