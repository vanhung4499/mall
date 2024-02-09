package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.portal.domain.PmsPortalProductDetail;
import com.hnv99.mall.portal.domain.PmsProductCategoryNode;

import java.util.List;

public interface PmsPortalProductService {
    /**
     * Comprehensive search for products
     */
    List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * Get all product categories in tree structure
     */
    List<PmsProductCategoryNode> categoryTreeList();

    /**
     * Get product details for the front end
     */
    PmsPortalProductDetail detail(Long id);
}
