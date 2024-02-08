package com.hnv99.mall.service;

import com.hnv99.mall.dto.PmsProductCategoryParam;
import com.hnv99.mall.dto.PmsProductCategoryWithChildrenItem;
import com.hnv99.mall.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductCategoryService {
    /**
     * Create product category
     */
    @Transactional
    int create(PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * Modify product category
     */
    @Transactional
    int update(Long id, PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * Get product categories with pagination
     */
    List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * Delete product category
     */
    int delete(Long id);

    /**
     * Get product category by ID
     */
    PmsProductCategory getItem(Long id);

    /**
     * Batch modify navigation status
     */
    int updateNavStatus(List<Long> ids, Integer navStatus);

    /**
     * Batch modify display status
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * Get product categories in a hierarchical form
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
