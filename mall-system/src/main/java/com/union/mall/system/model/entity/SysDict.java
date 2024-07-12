package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * Dictionary data entity
 *
 * @author vanhung4499
 */
@Data
public class SysDict implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Dictionary type code
     */
    private String typeCode;

    /**
     * Dictionary item name
     */
    private String name;

    /**
     * Dictionary item value
     */
    private String value;

    /**
     * Sorting order
     */
    private Integer sort;

    /**
     * Status (1: Normal; 0: Disabled)
     */
    private Integer status;

    /**
     * Default flag (1: Yes; 0: No)
     */
    private Integer defaulted;

    /**
     * Remarks
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
