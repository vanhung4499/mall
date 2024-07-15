package com.union.mall.oms;

import com.union.mall.pms.api.SkuFeignClient;
import com.union.mall.ums.api.MemberFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = { MemberFeignClient.class, SkuFeignClient.class})
public class MallOmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOmsApplication.class, args);
    }

}