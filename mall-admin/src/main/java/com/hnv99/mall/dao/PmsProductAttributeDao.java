package com.hnv99.mall.dao;

import com.hnv99.mall.dto.ProductAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductAttributeDao {
    /**
     * Get product attribute information
     */
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
