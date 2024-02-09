package com.hnv99.mall.portal.service;

import com.hnv99.mall.portal.domain.AliPayParam;

import java.util.Map;

public interface AlipayService {
    /**
     * Generate a computer payment page based on submitted parameters
     */
    String pay(AliPayParam aliPayParam);

    /**
     * Handle Alipay asynchronous callback
     */
    String notify(Map<String, String> params);

    /**
     * Query Alipay transaction status
     * @param outTradeNo Merchant order number
     * @param tradeNo Alipay transaction number
     * @return Alipay transaction status
     */
    String query(String outTradeNo, String tradeNo);

    /**
     * Generate a mobile payment page based on submitted parameters
     */
    String webPay(AliPayParam aliPayParam);
}