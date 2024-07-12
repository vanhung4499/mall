package com.union.mall.common.security.util;

import cn.hutool.core.convert.Convert;
import com.union.mall.common.core.constant.SystemConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
/**
 * Utility class for accessing Spring Security context information.
 * Provides methods to retrieve user-related attributes such as userId, username, roles, department ID,
 * and other custom attributes from the authentication token.
 *
 * This class is intended to centralize the retrieval of security context attributes to ensure consistent
 * access across the application.
 *
 * @author Ray Hao
 * @since 2.1.0
 */
public class SecurityUtils {

    /**
     * Retrieves the user ID from the authentication token attributes.
     *
     * @return User ID if available, null otherwise.
     */
    public static Long getUserId() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes != null) {
            return Convert.toLong(tokenAttributes.get("userId"));
        }
        return null;
    }

    /**
     * Retrieves the username from the current authentication context.
     *
     * @return Username if authenticated, null otherwise.
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Retrieves the entire token attributes from the authentication token.
     *
     * @return Token attributes map if the authentication is JwtAuthenticationToken, null otherwise.
     */
    public static Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes();
        }
        return null;
    }

    /**
     * Retrieves the roles of the current authenticated user.
     *
     * @return Set of roles, empty set if not authenticated.
     */
    public static Set<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return AuthorityUtils.authorityListToSet(authentication.getAuthorities())
                    .stream()
                    .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        }
        return Collections.emptySet();
    }

    /**
     * Retrieves the department ID from the authentication token attributes.
     *
     * @return Department ID if available, null otherwise.
     */
    public static Long getDeptId() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes != null) {
            return Convert.toLong(tokenAttributes.get("deptId"));
        }
        return null;
    }

    /**
     * Checks if the current user has a root role.
     *
     * @return true if the current user has the root role, false otherwise.
     */
    public static boolean isRoot() {
        Set<String> roles = getRoles();
        return roles.contains(SystemConstants.ROOT_ROLE_CODE);
    }

    /**
     * Retrieves the JTI (JSON Token ID) from the authentication token attributes.
     *
     * @return JTI if available, null otherwise.
     */
    public static String getJti() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes != null) {
            return String.valueOf(tokenAttributes.get("jti"));
        }
        return null;
    }

    /**
     * Retrieves the expiration time from the authentication token attributes.
     *
     * @return Expiration time if available, null otherwise.
     */
    public static Long getExp() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes != null) {
            return Convert.toLong(tokenAttributes.get("exp"));
        }
        return null;
    }

    /**
     * Retrieves the data scope (permission scope) from the authentication token attributes.
     *
     * @return Data scope if available, null otherwise.
     */
    public static Integer getDataScope() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes != null) {
            return Convert.toInt(tokenAttributes.get("dataScope"));
        }
        return null;
    }

    /**
     * Retrieves the member ID from the authentication token attributes.
     *
     * @return Member ID if available, null otherwise.
     */
    public static Long getMemberId() {
        Map<String, Object> tokenAttributes = getTokenAttributes();
        if (tokenAttributes != null) {
            return Convert.toLong(tokenAttributes.get("memberId"));
        }
        return null;
    }
}
