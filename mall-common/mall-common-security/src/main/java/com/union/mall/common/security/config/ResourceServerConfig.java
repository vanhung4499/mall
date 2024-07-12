package com.union.mall.common.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.union.mall.common.core.constant.JwtClaimConstants;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;


/**
 * Configuration class for setting up the Resource Server with JWT authentication.
 * Defines security filters, whitelist paths, JWT converter, and exception handling.
 *
 * This class configures access control based on JWT tokens, ignoring specific paths, and handling
 * authentication entry points and access denied scenarios.
 *
 * @author vanhung4499
 */

@ConfigurationProperties(prefix = "security")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class ResourceServerConfig {

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Whitelisted paths that are exempt from authentication.
     */
    @Setter
    private List<String> whitelistPaths;

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http           HttpSecurity object to configure security filters.
     * @param introspector   HandlerMappingIntrospector for MVC request matching.
     * @return SecurityFilterChain configured with access rules and exception handling.
     * @throws Exception if configuration encounters an error.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        log.info("Whitelist paths: {}", JSONUtil.toJsonStr(whitelistPaths));

        http.authorizeHttpRequests((requests) -> {
                    if (CollectionUtil.isNotEmpty(whitelistPaths)) {
                        for (String whitelistPath : whitelistPaths) {
                            requests.requestMatchers(mvcMatcherBuilder.pattern(whitelistPath)).permitAll();
                        }
                    }
                    requests.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);

        http.oauth2ResourceServer(resourceServerConfigurer ->
                resourceServerConfigurer
                        .jwt(jwtConfigurer -> jwtAuthenticationConverter())
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
        );

        return http.build();
    }

    /**
     * Configures paths to be ignored by the security filter chain.
     *
     * @return WebSecurityCustomizer to ignore specific request matchers.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/doc.html"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/swagger-ui/**")
        );
    }

    /**
     * Initializes the MvcRequestMatcher.Builder for MVC request matching.
     *
     * @param introspector HandlerMappingIntrospector for introspecting MVC mappings.
     * @return MvcRequestMatcher.Builder initialized with the introspector.
     */
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    /**
     * Configures a custom JWT authentication converter.
     *
     * @return Converter that converts JWT to AbstractAuthenticationToken.
     * @see JwtAuthenticationProvider#setJwtAuthenticationConverter(Converter)
     */
    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(Strings.EMPTY);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(JwtClaimConstants.AUTHORITIES);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
