package com.union.mall.system.api.fallback;

import com.union.mall.system.api.UserFeignClient;
import com.union.mall.system.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFeignFallbackClient implements UserFeignClient {

    @Override
    public UserAuthInfo getUserAuthInfo(String username) {
        log.error("Fallback method after a Feign remote call to the system user service fails");
        return new UserAuthInfo();
    }
}
