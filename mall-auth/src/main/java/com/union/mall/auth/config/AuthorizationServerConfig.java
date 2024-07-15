package com.union.mall.auth.config;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.union.mall.auth.model.SysUserDetails;
import com.union.mall.auth.oauth2.extension.password.PasswordAuthenticationConverter;
import com.union.mall.auth.oauth2.extension.password.PasswordAuthenticationProvider;
import com.union.mall.auth.oauth2.handler.MyAuthenticationFailureHandler;
import com.union.mall.auth.oauth2.handler.MyAuthenticationSuccessHandler;
import com.union.mall.auth.oauth2.jackson.SysUserMixin;
import com.union.mall.auth.oauth2.oidc.CustomOidcAuthenticationConverter;
import com.union.mall.auth.oauth2.oidc.CustomOidcAuthenticationProvider;
import com.union.mall.auth.oauth2.oidc.CustomOidcUserInfoService;
import com.union.mall.auth.service.MemberDetailsService;
import com.union.mall.common.core.constant.RedisConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * Authorization Server Configuration
 *
 * Configures the authorization server endpoints and security filters.
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/49b199c5b41b5f9279d9758fc2f5d24ed1fe4afa/samples/demo-authorizationserver/src/main/java/sample/config/AuthorizationServerConfig.java#L112">AuthorizationServerConfig</a>
 *
 * @author vanhung4499
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServerConfig {

    private final CustomOidcUserInfoService customOidcUserInfoService;

    private final OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer;

    private final StringRedisTemplate redisTemplate;


    /**
     * Configures the security filter chain for the authorization server endpoints.
     *
     * @param http                 HttpSecurity object for configuring HTTP security
     * @param authenticationManager AuthenticationManager for managing authentication
     * @param authorizationService OAuth2AuthorizationService for managing OAuth2 authorizations
     * @param tokenGenerator       OAuth2TokenGenerator for generating OAuth2 tokens
     * @return SecurityFilterChain configured for the authorization server
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator
    ) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                // Customizes token endpoint with custom converters and providers
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .accessTokenRequestConverters(
                                authenticationConverters ->
                                        authenticationConverters.addAll(
                                                List.of(
                                                        new PasswordAuthenticationConverter()
                                                )
                                        )
                        )
                        .authenticationProviders(
                                authenticationProviders ->
                                        authenticationProviders.add(
                                                new PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator)
                                        )
                        )
                        .accessTokenResponseHandler(new MyAuthenticationSuccessHandler()) // Custom success handler
                        .errorResponseHandler(new MyAuthenticationFailureHandler()) // Custom failure handler
                )
                // Enables OpenID Connect 1.0 customization
                .oidc(oidcCustomizer ->
                        oidcCustomizer.userInfoEndpoint(userInfoEndpointCustomizer ->
                                {
                                    userInfoEndpointCustomizer.userInfoRequestConverter(new CustomOidcAuthenticationConverter(customOidcUserInfoService));
                                    userInfoEndpointCustomizer.authenticationProvider(new CustomOidcAuthenticationProvider(authorizationService));
                                }
                        )
                );

        http
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }

    /**
     * JWK (JWT Key Set) Source
     */
    @Bean // <5>
    @SneakyThrows
    public JWKSource<SecurityContext> jwkSource() {

        // Attempt to retrieve JWKSet (JWT key set, including asymmetric encryption public and private keys) from Redis
        String jwkSetStr = redisTemplate.opsForValue().get(RedisConstants.JWK_SET_KEY);
        if (StrUtil.isNotBlank(jwkSetStr)) {
            // If exists, parse JWKSet and return
            JWKSet jwkSet = JWKSet.parse(jwkSetStr);
            return new ImmutableJWKSet<>(jwkSet);
        } else {
            // If JWKSet does not exist in Redis, generate a new JWKSet
            KeyPair keyPair = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            // Build RSAKey
            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();

            // Build JWKSet
            JWKSet jwkSet = new JWKSet(rsaKey);

            // Store JWKSet in Redis
            redisTemplate.opsForValue().set(RedisConstants.JWK_SET_KEY, jwkSet.toString(Boolean.FALSE));
            return new ImmutableJWKSet<>(jwkSet);
        }
    }

    /**
     * Generates an RSA key pair.
     *
     * @return KeyPair containing RSA public and private keys
     */
    private static KeyPair generateRsaKey() { // <6>
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * Authorization Server Configuration (token issuer, token acquisition endpoints, etc.)
     *
     * @return AuthorizationServerSettings instance
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * Password Encoder
     *
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

        // Initialize OAuth2 clients
        initMallAppClient(registeredClientRepository);
        initMallAdminClient(registeredClientRepository);

        return registeredClientRepository;
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           RegisteredClientRepository registeredClientRepository) {
        // Create JDBC-based OAuth2 authorization service. This service uses JdbcTemplate and client repository to store and retrieve OAuth2 authorization data.
        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);

        // Create and configure row mapper for handling OAuth2 authorization data in the database.
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
        rowMapper.setLobHandler(new DefaultLobHandler());
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        // You will need to write the Mixin for your class so Jackson can marshall it.

        // Add custom Mixin for serializing/deserializing specific classes.
        // Mixin classes need to be implemented separately so Jackson can handle serialization of these classes.
        objectMapper.addMixIn(SysUserDetails.class, SysUserMixin.class);
        objectMapper.addMixIn(Long.class, Object.class);

        // Set configured ObjectMapper to the row mapper.
        rowMapper.setObjectMapper(objectMapper);

        // Set custom row mapper to the authorization service.
        service.setAuthorizationRowMapper(rowMapper);

        return service;
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                         RegisteredClientRepository registeredClientRepository) {
        // Used by the ConsentController
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(jwtCustomizer);

        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Initialize the Mall Admin client.
     *
     * @param registeredClientRepository Repository for registered clients
     */
    private void initMallAdminClient(JdbcRegisteredClientRepository registeredClientRepository) {

        String clientId = "mall-admin";
        String clientSecret = "123456";
        String clientName = "Mall Admin Client";

    /*
      If using plain text, the client authentication method will automatically upgrade encryption method, so use bcrypt to avoid unnecessary troubles
      Official ISSUE: https://github.com/spring-projects/spring-authorization-server/issues/1099
     */
        String encodeSecret = passwordEncoder().encode(clientSecret);

        RegisteredClient registeredMallAdminClient = registeredClientRepository.findByClientId(clientId);
        String id = registeredMallAdminClient != null ? registeredMallAdminClient.getId() : UUID.randomUUID().toString();

        RegisteredClient mallAppClient = RegisteredClient.withId(id)
                .clientId(clientId)
                .clientSecret(encodeSecret)
                .clientName(clientName)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD) // Password grant type
                .redirectUri("http://127.0.0.1:8080/authorized")
                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(mallAppClient);
    }

    /**
     * Initialize the Mall App client.
     *
     * @param registeredClientRepository Repository for registered clients
     */
    private void initMallAppClient(JdbcRegisteredClientRepository registeredClientRepository) {

        String clientId = "mall-app";
        String clientSecret = "123456";
        String clientName = "Mall App Client";

        // If using plain text, the client authentication method will automatically upgrade encryption method, so use bcrypt to avoid unnecessary troubles
        String encodeSecret = passwordEncoder().encode(clientSecret);

        RegisteredClient registeredMallAppClient = registeredClientRepository.findByClientId(clientId);
        String id = registeredMallAppClient != null ? registeredMallAppClient.getId() : UUID.randomUUID().toString();

        RegisteredClient mallAppClient = RegisteredClient.withId(id)
                .clientId(clientId)
                .clientSecret(encodeSecret)
                .clientName(clientName)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/authorized")
                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(mallAppClient);
    }

}
