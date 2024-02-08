package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductFullReductionDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
