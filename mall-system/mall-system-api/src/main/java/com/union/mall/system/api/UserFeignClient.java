package com.union.mall.system.api;

import com.union.mall.common.web.config.FeignDecoderConfig;
import com.union.mall.system.api.fallback.UserFeignFallbackClient;
import com.union.mall.system.dto.UserAuthInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "mall-system", fallback = UserFeignFallbackClient.class, configuration = {FeignDecoderConfig.class})
public interface UserFeignClient {

    @GetMapping("/api/v1/users/{username}/authInfo")
    UserAuthInfo getUserAuthInfo(@PathVariable String username);
}
