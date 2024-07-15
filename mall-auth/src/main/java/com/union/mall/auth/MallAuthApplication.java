package com.union.mall.auth;

import com.union.mall.system.api.UserFeignClient;
import com.union.mall.ums.api.MemberFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = {UserFeignClient.class, MemberFeignClient.class})
@SpringBootApplication
@EnableDiscoveryClient
public class MallAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAuthApplication.class, args);
    }

}
