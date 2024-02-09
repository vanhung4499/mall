package com.hnv99.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hnv99.mall.mapper.OmsOrderMapper;
import com.hnv99.mall.portal.config.AlipayConfig;
import com.hnv99.mall.portal.domain.AliPayParam;
import com.hnv99.mall.portal.service.AlipayService;
import com.hnv99.mall.portal.service.OmsPortalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private OmsOrderMapper orderMapper;
    @Autowired
    private OmsPortalOrderService portalOrderService;
    @Override
    public String pay(AliPayParam aliPayParam) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        if(StrUtil.isNotEmpty(alipayConfig.getNotifyUrl())){
            // Asynchronous reception address, accessible from the internet
            request.setNotifyUrl(alipayConfig.getNotifyUrl());
        }
        if(StrUtil.isNotEmpty(alipayConfig.getReturnUrl())){
            // Synchronous redirection address
            request.setReturnUrl(alipayConfig.getReturnUrl());
        }
        //******Essential parameters******
        JSONObject bizContent = new JSONObject();
        // Merchant order number, customized by the merchant, must be unique
        bizContent.put("out_trade_no", aliPayParam.getOutTradeNo());
        // Payment amount, minimum value 0.01 yuan
        bizContent.put("total_amount", aliPayParam.getTotalAmount());
        // Order title, special symbols cannot be used
        bizContent.put("subject", aliPayParam.getSubject());
        // For computer website payment scene, the value "FAST_INSTANT_TRADE_PAY" is always used
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        String formHtml = null;
        try {
            formHtml = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return formHtml;
    }

    @Override
    public String notify(Map<String, String> params) {
        String result = "failure";
        boolean signVerified = false;
        try {
            // Call SDK to verify signature
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            log.error("Exception occurred while verifying callback signature!", e);
            e.printStackTrace();
        }
        if (signVerified) {
            String tradeStatus = params.get("trade_status");
            if("TRADE_SUCCESS".equals(tradeStatus)){
                result = "success";
                log.info("notify method was called, tradeStatus:{}", tradeStatus);
                String outTradeNo = params.get("out_trade_no");
                portalOrderService.paySuccessByOrderSn(outTradeNo,1);
            }else{
                log.warn("Order was not paid successfully, trade_status:{}", tradeStatus);
            }
        } else {
            log.warn("Failed to verify callback signature!");
        }
        return result;
    }

    @Override
    public String query(String outTradeNo, String tradeNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //******Essential parameters******
        JSONObject bizContent = new JSONObject();
        // Set query parameters, at least one of out_trade_no and trade_no must be passed
        if(StrUtil.isNotEmpty(outTradeNo)){
            bizContent.put("out_trade_no", outTradeNo);
        }
        if(StrUtil.isNotEmpty(tradeNo)){
            bizContent.put("trade_no", tradeNo);
        }
        // Trade settlement information: trade_settle_info
        String[] queryOptions = {"trade_settle_info"};
        bizContent.put("query_options", queryOptions);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("Exception occurred while querying Alipay bill!", e);
        }
        if(response.isSuccess()){
            log.info("Successfully queried Alipay bill!");
            if("TRADE_SUCCESS".equals(response.getTradeStatus())){
                portalOrderService.paySuccessByOrderSn(outTradeNo,1);
            }
        } else {
            log.error("Failed to query Alipay bill!");
        }
        // Trade status: WAIT_BUYER_PAY (trade created, waiting for buyer to pay), TRADE_CLOSED (unpaid trade timed out or fully refunded after payment), TRADE_SUCCESS (trade paid successfully), TRADE_FINISHED (trade ended, no refunds)
        return response.getTradeStatus();
    }

    @Override
    public String webPay(AliPayParam aliPayParam) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest ();
        if(StrUtil.isNotEmpty(alipayConfig.getNotifyUrl())){
            // Asynchronous callback address, accessible to the public network
            request.setNotifyUrl(alipayConfig.getNotifyUrl());
        }
        if(StrUtil.isNotEmpty(alipayConfig.getReturnUrl())){
            // Synchronous return address
            request.setReturnUrl(alipayConfig.getReturnUrl());
        }
        //******Essential parameters******
        JSONObject bizContent = new JSONObject();
        // Merchant order number, customized by the merchant, must be unique
        bizContent.put("out_trade_no", aliPayParam.getOutTradeNo());
        // Payment amount, minimum value is 0.01 yuan
        bizContent.put("total_amount", aliPayParam.getTotalAmount());
        // Order title, cannot use special symbols
        bizContent.put("subject", aliPayParam.getSubject());
        // For mobile website payment, default value is FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "QUICK_WAP_WAY");
        request.setBizContent(bizContent.toString());
        String formHtml = null;
        try {
            formHtml = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return formHtml;
    }

}
