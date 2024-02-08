package com.hnv99.mall.service;

import com.hnv99.mall.model.SmsCouponHistory;

import java.util.List;

public interface SmsCouponHistoryService {
    /**
     * Retrieves a paginated list of coupon histories.
     * @param couponId The ID of the coupon.
     * @param useStatus The usage status of the coupon.
     * @param orderSn The order number associated with the usage of the coupon.
     * @param pageSize The maximum number of coupon histories to be returned in one page.
     * @param pageNum The page number of the results to be returned.
     * @return A list of coupon histories.
     */
    List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum);
}
