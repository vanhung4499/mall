package com.union.mall.system.model.bo;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * User profile business object
 *
 * @author vanhung4499
 */
@Data
public class UserProfileBO {

    /**
     * User ID
     */
    private Long id;

    /**
     * Login username
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
     * Avatar URL
     */
    private String avatar;

    /**
     * Set of user role names
     */
    private Set<String> roleNames;

    /**
     * Department name
     */
    private String deptName;

    /**
     * Email address
     */
    private String email;

    /**
     * Gender
     */
    private Integer gender;

    /**
     * Creation time
     */
    private Date createTime;

}
