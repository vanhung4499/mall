package com.hnv99.mall.service;

import com.hnv99.mall.model.PmsSkuStock;

import java.util.List;

public interface PmsSkuStockService {
    /**
     * Fuzzy search by product ID and SKU code keyword
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * Bulk update product stock information
     */
    int update(Long pid, List<PmsSkuStock> skuStockList);
}
