package com.union.mall.auth.oauth2.oidc;

import com.union.mall.system.api.UserFeignClient;
import com.union.mall.system.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Custom OIDC User Information Service
 *
 * Provides custom OIDC user information loading based on user authentication details.
 *
 * @author vanhung4499
 */
@Service
@Slf4j
public class CustomOidcUserInfoService {

    private final UserFeignClient userFeignClient;

    public CustomOidcUserInfoService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    /**
     * Loads user information by username using Feign client.
     *
     * @param username Username of the user
     * @return CustomOidcUserInfo containing OIDC user information
     */
    public CustomOidcUserInfo loadUserByUsername(String username) {
        UserAuthInfo userAuthInfo;
        try {
            userAuthInfo = userFeignClient.getUserAuthInfo(username);
            if (userAuthInfo == null) {
                return null;
            }
            return new CustomOidcUserInfo(createUser(userAuthInfo));
        } catch (Exception e) {
            log.error("Failed to fetch user information", e);
            return null;
        }
    }

    /**
     * Creates a map of user attributes from UserAuthInfo.
     *
     * @param userAuthInfo UserAuthInfo object containing user authentication details
     * @return Map of user attributes for OIDC UserInfo
     */
    private Map<String, Object> createUser(UserAuthInfo userAuthInfo) {
        return CustomOidcUserInfo.customBuilder()
                .username(userAuthInfo.getUsername())
                .nickname(userAuthInfo.getNickname())
                .status(userAuthInfo.getStatus())
                .phoneNumber(userAuthInfo.getMobile())
                .email(userAuthInfo.getEmail())
                .profile(userAuthInfo.getAvatar())
                .build()
                .getClaims();
    }
}
