package com.union.mall.auth.oauth2.oidc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;

/**
 * Custom OIDC Authentication Provider
 *
 * Provides custom OIDC authentication based on access token validation and scope-based user info mapping.
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Slf4j
public class CustomOidcAuthenticationProvider implements AuthenticationProvider {

    private final OAuth2AuthorizationService authorizationService;

    public CustomOidcAuthenticationProvider(OAuth2AuthorizationService authorizationService) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.authorizationService = authorizationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OidcUserInfoAuthenticationToken userInfoAuthentication = (OidcUserInfoAuthenticationToken) authentication;
        AbstractOAuth2TokenAuthenticationToken<?> accessTokenAuthentication = null;
        if (AbstractOAuth2TokenAuthenticationToken.class.isAssignableFrom(userInfoAuthentication.getPrincipal().getClass())) {
            accessTokenAuthentication = (AbstractOAuth2TokenAuthenticationToken) userInfoAuthentication.getPrincipal();
        }

        if (accessTokenAuthentication != null && accessTokenAuthentication.isAuthenticated()) {
            String accessTokenValue = accessTokenAuthentication.getToken().getTokenValue();
            OAuth2Authorization authorization = this.authorizationService.findByToken(accessTokenValue, OAuth2TokenType.ACCESS_TOKEN);
            if (authorization == null) {
                throw new OAuth2AuthenticationException("invalid_token");
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Retrieved authorization with access token");
                }

                OAuth2Authorization.Token<OAuth2AccessToken> authorizedAccessToken = authorization.getAccessToken();
                if (!authorizedAccessToken.isActive()) {
                    throw new OAuth2AuthenticationException("invalid_token");
                } else {
                    // Extract userInfo from authentication result
                    CustomOidcUserInfo customOidcUserInfo = (CustomOidcUserInfo) userInfoAuthentication.getUserInfo();
                    // Extract authorization scope from authorizedAccessToken
                    Set<String> scopeSet = (HashSet<String>) authorizedAccessToken.getClaims().get("scope");
                    // Get userInfo fields corresponding to authorization scopes
                    Map<String, Object> claims = DefaultOidcUserInfoMapper.getClaimsRequestedByScope(customOidcUserInfo.getClaims(), scopeSet);
                    if (log.isTraceEnabled()) {
                        log.trace("Authenticated user info request");
                    }

                    return new CustomOidcToken(accessTokenAuthentication, new CustomOidcUserInfo(claims));
                }
            }
        } else {
            throw new OAuth2AuthenticationException("invalid_token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OidcUserInfoAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Default OIDC User Info Mapper
     */
    private static final class DefaultOidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, CustomOidcUserInfo> {
        private static final List<String> EMAIL_CLAIMS = Arrays.asList("email", "email_verified");
        private static final List<String> PHONE_CLAIMS = Arrays.asList("phone_number", "phone_number_verified");
        private static final List<String> PROFILE_CLAIMS = Arrays.asList("username", "nickname", "status", "profile");

        private DefaultOidcUserInfoMapper() {
        }

        @Override
        public CustomOidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
            OAuth2Authorization authorization = authenticationContext.getAuthorization();
            OidcIdToken idToken = authorization.getToken(OidcIdToken.class).getToken();
            OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
            Map<String, Object> scopeRequestedClaims = getClaimsRequestedByScope(idToken.getClaims(), accessToken.getScopes());
            return new CustomOidcUserInfo(scopeRequestedClaims);
        }

        /**
         * Extracts different data based on different permissions
         */
        private static Map<String, Object> getClaimsRequestedByScope(Map<String, Object> claims, Set<String> requestedScopes) {
            Set<String> scopeRequestedClaimNames = new HashSet<>(32);
            scopeRequestedClaimNames.add("sub");

            if (requestedScopes.contains("address")) {
                scopeRequestedClaimNames.add("address");
            }

            if (requestedScopes.contains("email")) {
                scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
            }

            if (requestedScopes.contains("phone")) {
                scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
            }

            if (requestedScopes.contains("profile")) {
                scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
            }

            Map<String, Object> requestedClaims = new HashMap<>(claims);
            requestedClaims.keySet().removeIf((claimName) -> !scopeRequestedClaimNames.contains(claimName));
            return requestedClaims;
        }
    }
}
