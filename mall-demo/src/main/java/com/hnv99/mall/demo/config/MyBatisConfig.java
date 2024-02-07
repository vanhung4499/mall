package com.hnv99.mall.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.hnv99.mall.mapper")
public class MyBatisConfig {
}
