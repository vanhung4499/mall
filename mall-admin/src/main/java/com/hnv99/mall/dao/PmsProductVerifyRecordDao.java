package com.hnv99.mall.dao;

import com.hnv99.mall.model.PmsProductVerifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductVerifyRecordDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<PmsProductVerifyRecord> list);
}
