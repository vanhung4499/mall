package com.hnv99.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.hnv99.mall.mapper","com.hnv99.mall.dao"})
public class MyBatisConfig {
}
