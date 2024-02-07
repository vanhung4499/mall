package com.hnv99.mall.config;

import com.hnv99.mall.common.config.BaseSwaggerConfig;
import com.hnv99.mall.common.domain.SwaggerProperties;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hnv99.mall.controller")
                .title("mall admin system")
                .description("Documentation for mall admin related interfaces")
                .contactName("Hung Nguyen")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }


    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor(); // Generate a BeanPostProcessor
    }

}
