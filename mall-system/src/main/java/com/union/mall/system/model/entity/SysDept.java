package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * Department table
 *
 * @author vanhung4499
 */
@Data
public class SysDept extends BaseEntity {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Department name
     */
    private String name;

    /**
     * Parent node ID
     */
    private Long parentId;

    /**
     * Parent node ID path
     */
    private String treePath;

    /**
     * Display order
     */
    private Integer sort;

    /**
     * Status (1: Normal; 0: Disabled)
     */
    private Integer status;

    /**
     * Logical deletion flag (1: Deleted; 0: Not deleted)
     */
    private Integer deleted;

}
