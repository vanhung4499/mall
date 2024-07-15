package com.union.mall.gateway.config;


import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.union.mall.common.core.result.ResultCode;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Configuration
public class SentinelConfig {

    @PostConstruct
    private void initBlockHandler() {
        GatewayCallbackManager.setBlockHandler(
                (exchange, t) ->
                        ServerResponse
                                .status(HttpStatus.TOO_MANY_REQUESTS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromValue(ResultCode.FLOW_LIMITING.toString())
                                )
        );
    }
}
