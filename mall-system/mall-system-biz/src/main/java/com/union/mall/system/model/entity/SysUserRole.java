package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    /**
     * User ID
     */
    private Long userId;

    /**
     * Role ID
     */
    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}