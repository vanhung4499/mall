package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@TableName(value = "sys_role")
@Data
public class SysRole extends BaseEntity {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Role name
     */
    private String name;

    /**
     * Role code
     */
    private String code;

    /**
     * Display order
     */
    private Integer sort;

    /**
     * Role status (1: Active; 0: Disabled)
     */
    private Integer status;

    /**
     * Logical deletion flag (0: Not deleted; 1: Deleted)
     */
    private Integer deleted;

    /**
     * Data scope
     */
    private Integer dataScope;
}
