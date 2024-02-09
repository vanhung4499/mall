package com.hnv99.mall.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    /**
     * Filter that allows cross-origin calls
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow all domain names to make cross-origin calls
        config.addAllowedOriginPattern("*");
        // Allow cross-origin requests to send cookies
        config.setAllowCredentials(true);
        // Allow all original header information
        config.addAllowedHeader("*");
        // Allow all request methods for cross-origin calls
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
