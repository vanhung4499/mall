package com.hnv99.mall.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.hnv99.mall.mapper","com.hnv99.mall.search.dao"})
public class MyBatisConfig {
}
