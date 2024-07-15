package com.union.mall.pms.model.entity;

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
public class PmsSpuAttribute extends BaseEntity {

    /**
     * Primary key ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Product ID
     */
    private Long spuId;

    /**
     * Attribute ID
     */
    private Long attributeId;

    /**
     * Attribute name
     */
    private String name;

    /**
     * Attribute value
     */
    private String value;

    /**
     * Attribute type (1: Specification; 2: Attribute)
     */
    private Integer type;

    /**
     * Specification image URL
     */
    private String picUrl;
}
