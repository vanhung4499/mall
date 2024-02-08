package com.hnv99.mall.dao;

import com.hnv99.mall.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsCouponProductRelationDao {
    /**
     * Batch creation
     */
    int insertList(@Param("list") List<SmsCouponProductRelation> productRelationList);
}
