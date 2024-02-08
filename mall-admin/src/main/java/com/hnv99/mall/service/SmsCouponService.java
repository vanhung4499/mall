package com.hnv99.mall.service;

import com.hnv99.mall.dto.SmsCouponParam;
import com.hnv99.mall.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsCouponService {
    /**
     * Add a coupon
     */
    @Transactional
    int create(SmsCouponParam couponParam);

    /**
     * Delete a coupon by its ID
     */
    @Transactional
    int delete(Long id);

    /**
     * Update coupon information by its ID
     */
    @Transactional
    int update(Long id, SmsCouponParam couponParam);

    /**
     * Get a paginated list of coupons
     */
    List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);

    /**
     * Get coupon details
     * @param id ID of the coupon
     */
    SmsCouponParam getItem(Long id);
}
