package com.hnv99.mall.service;

import com.hnv99.mall.model.UmsResourceCategory;

import java.util.List;

public interface UmsResourceCategoryService {

    /**
     * Get all resource categories
     */
    List<UmsResourceCategory> listAll();

    /**
     * Create a resource category
     */
    int create(UmsResourceCategory umsResourceCategory);

    /**
     * Update a resource category
     */
    int update(Long id, UmsResourceCategory umsResourceCategory);

    /**
     * Delete a resource category
     */
    int delete(Long id);
}
