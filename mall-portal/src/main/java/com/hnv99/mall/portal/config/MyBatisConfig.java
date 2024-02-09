package com.hnv99.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.hnv99.mall.mapper","com.hnv99.mall.portal.dao"})
public class MyBatisConfig {
}
