package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductCategoryAttributeRelationDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList);
}
