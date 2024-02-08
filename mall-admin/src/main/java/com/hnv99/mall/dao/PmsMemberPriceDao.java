package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsMemberPriceDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);
}
