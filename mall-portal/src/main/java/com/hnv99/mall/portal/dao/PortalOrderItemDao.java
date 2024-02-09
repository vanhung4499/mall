package com.hnv99.mall.portal.dao;

import com.hnv99.mall.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalOrderItemDao {
    /**
     * Batch insert
     */
    int insertList(@Param("list") List<OmsOrderItem> list);
}

