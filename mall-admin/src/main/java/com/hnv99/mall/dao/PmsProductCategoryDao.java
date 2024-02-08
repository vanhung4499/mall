package com.hnv99.mall.dao;

import com.hnv99.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

public interface PmsProductCategoryDao {
    /**
     * Get product categories and their subcategories
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
