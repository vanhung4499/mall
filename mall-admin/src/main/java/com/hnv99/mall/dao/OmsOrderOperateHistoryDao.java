package com.hnv99.mall.dao;

import com.hnv99.mall.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderOperateHistoryDao {
    /**
     * Batch creation
     */
    int insertList(@Param("list") List<OmsOrderOperateHistory> orderOperateHistoryList);
}
