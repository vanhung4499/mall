package com.union.mall.pms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {MemberFeignClient.class})
public class MallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPmsApplication.class, args);
    }

}
