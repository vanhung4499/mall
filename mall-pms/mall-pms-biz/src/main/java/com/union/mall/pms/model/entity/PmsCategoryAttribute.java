package com.union.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PmsCategoryAttribute extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Product category ID
     */
    private Long categoryId;

    /**
     * Attribute/Specification name
     */
    private String name;

    /**
     * Type (1: Specification; 2: Attribute)
     */
    private Integer type;
}
