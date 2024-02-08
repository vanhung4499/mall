package com.hnv99.mall.service;

import com.hnv99.mall.dto.PmsProductAttributeCategoryItem;
import com.hnv99.mall.model.PmsProductAttributeCategory;

import java.util.List;

public interface PmsProductAttributeCategoryService {
    /**
     * Create attribute category
     */
    int create(String name);

    /**
     * Update attribute category
     */
    int update(Long id, String name);

    /**
     * Delete attribute category
     */
    int delete(Long id);

    /**
     * Get attribute category details
     */
    PmsProductAttributeCategory getItem(Long id);

    /**
     * Paginated query of attribute categories
     */
    List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum);

    /**
     * Get attribute categories with attributes
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
