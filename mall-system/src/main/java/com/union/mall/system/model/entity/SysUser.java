package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class SysUser extends BaseEntity {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Username
     */
    private String username;

    /**
     * Nickname
     */
    private String nickname;

    /**
     * Gender ((1: Male; 2: Female))
     */
    private Integer gender;

    /**
     * Password
     */
    private String password;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * User avatar
     */
    private String avatar;

    /**
     * Contact number
     */
    private String mobile;

    /**
     * User status (1: Active; 0: Disabled)
     */
    private Integer status;

    /**
     * User email
     */
    private String email;

    /**
     * Logical deletion flag (0: Not deleted; 1: Deleted)
     */
    private Integer deleted;

}
