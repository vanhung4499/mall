package com.hnv99.mall.service;

import com.hnv99.mall.model.UmsResource;

import java.util.List;

public interface UmsResourceService {
    /**
     * Add a resource
     */
    int create(UmsResource umsResource);

    /**
     * Update a resource
     */
    int update(Long id, UmsResource umsResource);

    /**
     * Get resource details
     */
    UmsResource getItem(Long id);

    /**
     * Delete a resource
     */
    int delete(Long id);

    /**
     * Paginated query of resources
     */
    List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    /**
     * Query all resources
     */
    List<UmsResource> listAll();
}
