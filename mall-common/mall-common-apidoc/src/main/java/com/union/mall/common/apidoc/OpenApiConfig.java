package com.union.mall.common.apidoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


/**
 * OpenAPI configuration class
 * <p>
 * Based on OpenAPI 3.0 specification + SpringDoc implementation + knife4j enhancements
 *
 * @author vanhung4499
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ApiDocInfoProperties.class)
public class OpenApiConfig {

    /**
     * OAuth2 authentication endpoint
     */
    @Value("${spring.security.oauth2.authorizationserver.token-uri}")
    private String tokenUrl;

    /**
     * API documentation information properties
     */
    private final ApiDocInfoProperties apiDocInfoProperties;

    /**
     * OpenAPI configuration (metadata, security protocols)
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                new SecurityScheme()
                                        // OAuth2 authorization mode
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .flows(new OAuthFlows()
                                                .password(
                                                        new OAuthFlow()
                                                                .tokenUrl(tokenUrl)
                                                                .refreshUrl(tokenUrl)
                                                )
                                        )
                                        // Security scheme using Bearer token (JWT)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                        )
                )
                // Globally add Authorization parameter to all endpoints
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                // API documentation information (non-critical)
                .info(new Info()
                        .title(apiDocInfoProperties.getTitle())
                        .version(apiDocInfoProperties.getVersion())
                        .description(apiDocInfoProperties.getDescription())
                        .contact(new Contact()
                                .name(apiDocInfoProperties.getContact().getName())
                                .url(apiDocInfoProperties.getContact().getUrl())
                                .email(apiDocInfoProperties.getContact().getEmail())
                        )
                        .license(new License()
                                .name(apiDocInfoProperties.getLicense().getName())
                                .url(apiDocInfoProperties.getLicense().getUrl())
                        ));
    }
}
