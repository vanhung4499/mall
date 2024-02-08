package com.hnv99.mall.service;

import com.hnv99.mall.dto.PmsProductAttributeParam;
import com.hnv99.mall.dto.ProductAttrInfo;
import com.hnv99.mall.model.PmsProductAttribute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductAttributeService {
    /**
     * Get product attributes by category ID and type
     * @param cid category id
     * @param type 0->specification; 1->parameter
     */
    List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

    /**
     * Add product attribute
     */
    @Transactional
    int create(PmsProductAttributeParam pmsProductAttributeParam);

    /**
     * Update product attribute
     */
    int update(Long id, PmsProductAttributeParam productAttributeParam);

    /**
     * Get single product attribute information
     */
    PmsProductAttribute getItem(Long id);

    /**
     * Batch delete product attributes
     */
    @Transactional
    int delete(List<Long> ids);

    /**
     * Get attribute list corresponding to product category
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
