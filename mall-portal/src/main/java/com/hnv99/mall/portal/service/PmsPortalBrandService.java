package com.hnv99.mall.portal.service;

import com.hnv99.mall.common.api.CommonPage;
import com.hnv99.mall.model.PmsBrand;
import com.hnv99.mall.model.PmsProduct;

import java.util.List;

public interface PmsPortalBrandService {
    /**
     * Get recommended brands by page
     */
    List<PmsBrand> recommendList(Integer pageNum, Integer pageSize);

    /**
     * Get brand details
     */
    PmsBrand detail(Long brandId);

    /**
     * Get products associated with the brand by page
     */
    CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize);
}
