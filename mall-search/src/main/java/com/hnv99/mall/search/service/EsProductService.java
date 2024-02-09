package com.hnv99.mall.search.service;

import com.hnv99.mall.search.domain.EsProduct;
import com.hnv99.mall.search.domain.EsProductRelatedInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EsProductService {
    /**
     * Import all products from the database to Elasticsearch.
     */
    int importAll();

    /**
     * Delete a product by its ID.
     */
    void delete(Long id);

    /**
     * Create a product by its ID.
     */
    EsProduct create(Long id);

    /**
     * Delete multiple products by their IDs.
     */
    void delete(List<Long> ids);

    /**
     * Search for products by keyword in the name or subtitle.
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * Perform a compound search for products by keyword in the name or subtitle, brand ID, and product category ID.
     */
    Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * Recommend products related to a product with a specific ID.
     */
    Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize);

    /**
     * Search for brands, categories, and attributes related to a keyword.
     */
    EsProductRelatedInfo searchRelatedInfo(String keyword);
}

