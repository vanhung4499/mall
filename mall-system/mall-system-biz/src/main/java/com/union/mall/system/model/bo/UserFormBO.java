package com.union.mall.system.model.bo;

import lombok.Data;

import java.util.List;


/**
 * User form business object
 *
 * @author vanhung4499
 */
@Data
public class UserFormBO {

    /**
     * User ID
     */
    private Long id;

    /**
     * Username
     */
    private String username;

    /**
     * User nickname
     */
    private String nickname;

    /**
     * Mobile number
     */
    private String mobile;

    /**
     * Gender (1: male; 2: female)
     */
    private Integer gender;

    /**
     * User avatar URL
     */
    private String avatar;

    /**
     * User email address
     */
    private String email;

    /**
     * Status (1: enabled; 0: disabled)
     */
    private Integer status;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Collection of role IDs
     */
    private List<Long> roleIds;

}
