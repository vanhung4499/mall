package com.hnv99.mall.portal.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayClientConfig {

    // Define a Bean for AlipayClient
    @Bean
    public AlipayClient alipayClient(AlipayConfig config){
        // Return a new instance of DefaultAlipayClient with the properties fetched from AlipayConfig
        return new DefaultAlipayClient(
                config.getGatewayUrl(),
                config.getAppId(),
                config.getAppPrivateKey(),
                config.getFormat(),
                config.getCharset(),
                config.getAlipayPublicKey(),
                config.getSignType()
        );
    }
}

