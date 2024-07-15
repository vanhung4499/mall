package com.union.mall.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Promotion System for Discounts
 * Seckill Feature Development
 * 1. Develop Seckill Activity Management Interface for Admin Panel
 *    (Create Seckill Sessions, Associate Seckill Sessions with Products)
 * 2. Seckill Warm-up: Use asynchronous + scheduled tasks to synchronize
 *    seckill data to Redis in advance.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class MallSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallSmsApplication.class, args);
    }
}
