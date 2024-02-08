package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuStockDao {
    /**
     * Batch insert operation
     */
    int insertList(@Param("list") List<PmsSkuStock> skuStockList);

    /**
     * Batch insert or replace operation
     */
    int replaceList(@Param("list") List<PmsSkuStock> skuStockList);
}
