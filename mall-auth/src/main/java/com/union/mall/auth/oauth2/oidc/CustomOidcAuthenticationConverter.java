package com.union.mall.auth.oauth2.oidc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;

/**
 * Custom OIDC Authentication Converter
 *
 * Converts OIDC authentication using a custom UserInfo service.
 *
 * @author vanhung4499
 */
public class CustomOidcAuthenticationConverter implements AuthenticationConverter {

    private final CustomOidcUserInfoService customOidcUserInfoService;

    public CustomOidcAuthenticationConverter(CustomOidcUserInfoService customOidcUserInfoService) {
        this.customOidcUserInfoService = customOidcUserInfoService;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOidcUserInfo customOidcUserInfo = customOidcUserInfoService.loadUserByUsername(authentication.getName());
        return new OidcUserInfoAuthenticationToken(authentication, customOidcUserInfo);
    }
}
