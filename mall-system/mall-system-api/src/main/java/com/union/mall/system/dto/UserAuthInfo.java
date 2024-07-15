package com.union.mall.system.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * User Authentication Information Transfer Object
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
     * User Password
     */
    private String password;

    /**
     * User Status (1: Active; 0: Disabled)
     */
    private Integer status;

    /**
     * Set of User Role Codes ["ROOT", "ADMIN"]
     */
    private Set<String> roles;

    /**
     * Set of User Permission Identifiers
     */
    private Set<String> perms;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Data Permission Scope
     */
    private Integer dataScope;

    /**
     * Nickname (OIDC UserInfo)
     */
    private String nickname;

    /**
     * Mobile Number (OIDC UserInfo)
     */
    private String mobile;

    /**
     * Email (OIDC UserInfo)
     */
    private String email;

    /**
     * Avatar (OIDC UserInfo)
     */
    private String avatar;

}
