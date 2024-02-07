package com.hnv99.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    /**
     * Filter allowing cross-origin requests
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow all domains to make cross-origin requests
        config.addAllowedOriginPattern("*");
        // Allow credentials to be sent with the cross-origin requests
        config.setAllowCredentials(true);
        // Allow all original header information
        config.addAllowedHeader("*");
        // Allow all request methods for cross-origin requests
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

