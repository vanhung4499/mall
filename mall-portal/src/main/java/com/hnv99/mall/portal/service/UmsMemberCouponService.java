package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.SmsCoupon;
import com.hnv99.mall.model.SmsCouponHistory;
import com.hnv99.mall.portal.domain.CartPromotionItem;
import com.hnv99.mall.portal.domain.SmsCouponHistoryDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsMemberCouponService {
    /**
     * Member adds a coupon
     */
    @Transactional
    void add(Long couponId);

    /**
     * Get the coupon history list
     */
    List<SmsCouponHistory> listHistory(Integer useStatus);

    /**
     * Get available coupons based on shopping cart information
     */
    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartItemList, Integer type);

    /**
     * Get coupons related to the current product
     */
    List<SmsCoupon> listByProduct(Long productId);

    /**
     * Get the user's coupon list
     */
    List<SmsCoupon> list(Integer useStatus);
}
