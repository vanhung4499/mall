package com.union.mall.pms.model.entity;

import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class PmsCategoryBrand extends BaseEntity {
    /**
     * Category ID
     */
    private Long categoryId;

    /**
     * Brand ID
     */
    private Long brandId;
}
