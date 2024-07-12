package com.union.mall.system.model.dto;

import lombok.Data;

import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class UserAuthInfo {

    /**
     * User ID
     */
    private Long userId;

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;

    /**
     * User status (1: Active; 0: Disabled)
     */
    private Integer status;

    /**
     * Set of role codes for the user ["ROOT", "ADMIN"]
     */
    private Set<String> roles;

    /**
     * Set of permission identifiers for the user
     */
    private Set<String> perms;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Data scope range
     */
    private Integer dataScope;

    /**
     * Nickname (OIDC UserInfo)
     */
    private String nickname;

    /**
     * Mobile number (OIDC UserInfo)
     */
    private String mobile;

    /**
     * Email address (OIDC UserInfo)
     */
    private String email;

    /**
     * Avatar URL (OIDC UserInfo)
     */
    private String avatar;
}
