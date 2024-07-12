package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * Dictionary type entity
 *
 * @author vanhung4499
 */
@Data
public class SysDictType extends BaseEntity {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Type name
     */
    private String name;

    /**
     * Type code
     */
    private String code;

    /**
     * Status (0: Normal; 1: Disabled)
     */
    private Integer status;

    /**
     * Remark
     */
    private String remark;
}
