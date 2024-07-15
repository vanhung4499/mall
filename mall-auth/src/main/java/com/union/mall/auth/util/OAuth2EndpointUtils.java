package com.union.mall.auth.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2 Endpoint utility class.
 *
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2EndpointUtils
 * @author vanhung4499
 */
public class OAuth2EndpointUtils {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    public OAuth2EndpointUtils() {
    }

    /**
     * Retrieves parameters from the HttpServletRequest.
     *
     * @param request HttpServletRequest containing parameters
     * @return MultiValueMap containing request parameters
     */
    public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }

    /**
     * Retrieves parameters if the request matches an authorization code grant request.
     *
     * @param request    HttpServletRequest to check
     * @param exclusions Parameters to exclude from the returned map
     * @return Map of parameters if matches, empty map otherwise
     */
    static Map<String, Object> getParametersIfMatchesAuthorizationCodeGrantRequest(HttpServletRequest request, String... exclusions) {
        if (!matchesAuthorizationCodeGrantRequest(request)) {
            return Collections.emptyMap();
        }
        Map<String, Object> parameters = new HashMap<>(getParameters(request).toSingleValueMap());
        for (String exclusion : exclusions) {
            parameters.remove(exclusion);
        }
        return parameters;
    }

    /**
     * Checks if the request matches an authorization code grant request.
     *
     * @param request HttpServletRequest to check
     * @return true if matches, false otherwise
     */
    static boolean matchesAuthorizationCodeGrantRequest(HttpServletRequest request) {
        return AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(
                request.getParameter(OAuth2ParameterNames.GRANT_TYPE)) &&
                request.getParameter(OAuth2ParameterNames.CODE) != null;
    }

    /**
     * Checks if the request matches a PKCE token request.
     *
     * @param request HttpServletRequest to check
     * @return true if matches, false otherwise
     */
    static boolean matchesPkceTokenRequest(HttpServletRequest request) {
        return matchesAuthorizationCodeGrantRequest(request) &&
                request.getParameter(PkceParameterNames.CODE_VERIFIER) != null;
    }

    /**
     * Throws an OAuth2AuthenticationException with the specified error details.
     *
     * @param errorCode     Error code
     * @param parameterName Parameter name causing the error
     * @param errorUri      URI reference for error details
     */
    public static void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }

    /**
     * Normalizes user code by ensuring it is uppercase and formatted with dashes.
     *
     * @param userCode User code to normalize
     * @return Normalized user code
     */
    static String normalizeUserCode(String userCode) {
        Assert.hasText(userCode, "userCode cannot be empty");
        StringBuilder sb = new StringBuilder(userCode.toUpperCase().replaceAll("[^A-Z\\d]+", ""));
        Assert.isTrue(sb.length() == 8, "userCode must be exactly 8 alpha/numeric characters");
        sb.insert(4, '-');
        return sb.toString();
    }
}
