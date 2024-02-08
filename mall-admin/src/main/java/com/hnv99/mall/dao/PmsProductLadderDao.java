package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductLadderDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}
