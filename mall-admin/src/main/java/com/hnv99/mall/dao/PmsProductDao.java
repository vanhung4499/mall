package com.hnv99.mall.dao;

import com.hnv99.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

public interface PmsProductDao {
    /**
     * Get product edit information
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
