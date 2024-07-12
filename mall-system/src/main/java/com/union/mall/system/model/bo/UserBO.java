package com.union.mall.system.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * User business object
 *
 * @author vanhung4499
 */
@Data
public class UserBO {

    /**
     * User ID
     */
    private Long id;

    /**
     * Account name (username)
     */
    private String username;

    /**
     * Nickname
     */
    private String nickname;

    /**
     * Mobile phone number
     */
    private String mobile;

    /**
     * Gender (1->Male; 2->Female)
     */
    private Integer gender;

    /**
     * Avatar URL
     */
    private String avatar;

    /**
     * Email address
     */
    private String email;

    /**
     * Status: 1->Enabled; 0->Disabled
     */
    private Integer status;

    /**
     * Department name
     */
    private String deptName;

    /**
     * Role names, separated by commas for multiple roles
     */
    private String roleNames;

    /**
     * Creation time
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
