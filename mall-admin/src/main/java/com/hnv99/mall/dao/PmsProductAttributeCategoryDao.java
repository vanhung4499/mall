package com.hnv99.mall.dao;

import com.hnv99.mall.dto.PmsProductAttributeCategoryItem;

import java.util.List;

public interface PmsProductAttributeCategoryDao {
    /**
     * Get product attribute categories that include attributes
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
