package com.hnv99.mall.demo.config;

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
                .apiBasePackage("com.hnv99.mall.demo.controller") // Base package for the API
                .title("mall-demo system") // Title for the API documentation
                .description("Some examples in the SpringBoot version") // Description for the API documentation
                .contactName("macro") // Contact name for the API documentation
                .version("1.0") // Version of the API
                .enableSecurity(true) // Enable security for the API
                .build();
    }

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor(); // Generate a BeanPostProcessor
    }

}
