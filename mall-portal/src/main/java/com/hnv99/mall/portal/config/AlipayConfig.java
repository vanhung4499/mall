package com.hnv99.mall.portal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    /**
     * Alipay gateway
     */
    private String gatewayUrl;
    /**
     * Application ID
     */
    private String appId;
    /**
     * Application private key
     */
    private String appPrivateKey;
    /**
     * Alipay public key
     */
    private String alipayPublicKey;
    /**
     * The return URL that Alipay calls after the user confirms payment
     * For the development environment: http://localhost:8060/#/pages/money/paySuccess
     */
    private String returnUrl;
    /**
     * After successful payment, Alipay's server actively notifies the merchant's server's asynchronous notification callback (requires public network access)
     * For the development environment: http://localhost:8085/alipay/notify
     */
    private String notifyUrl;
    /**
     * Parameter return format, only supports JSON
     */
    private String format = "JSON";
    /**
     * The encoding format used by the request
     */
    private String charset = "UTF-8";
    /**
     * The signature algorithm type used to generate the signature string
     */
    private String signType = "RSA2";
}

