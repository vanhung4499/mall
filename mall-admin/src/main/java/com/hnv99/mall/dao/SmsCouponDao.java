package com.hnv99.mall.dao;

import com.hnv99.mall.dto.SmsCouponParam;
import org.apache.ibatis.annotations.Param;

public interface SmsCouponDao {
    /**
     * Get coupon details including binding relationship
     */
    SmsCouponParam getItem(@Param("id") Long id);
}
