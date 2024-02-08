package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductAttributeValueDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<PmsProductAttributeValue> productAttributeValueList);
}
