package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.CmsSubject;
import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.model.PmsProductCategory;
import com.hnv99.mall.portal.domain.HomeContentResult;

import java.util.List;

public interface HomeService {

    /**
     * Get home page content
     */
    HomeContentResult content();

    /**
     * Home page product recommendation
     */
    List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum);

    /**
     * Get product categories
     * @param parentId 0: Get primary categories; Other: Get specified secondary categories
     */
    List<PmsProductCategory> getProductCateList(Long parentId);

    /**
     * Get subjects by category with pagination
     * @param cateId Subject category id
     */
    List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum);

    /**
     * Get popular recommended products with pagination
     */
    List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize);

    /**
     * Get new product recommendations with pagination
     */
    List<PmsProduct> newProductList(Integer pageNum, Integer pageSize);
}
