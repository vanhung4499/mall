package com.hnv99.mall.search.dao;

import com.hnv99.mall.search.domain.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EsProductDao {
    /**
     * Fetch the search product with the specified ID.
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
