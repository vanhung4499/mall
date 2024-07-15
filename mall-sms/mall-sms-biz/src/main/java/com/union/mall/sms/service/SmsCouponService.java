package com.union.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.sms.model.entity.SmsCoupon;
import com.union.mall.sms.model.form.CouponForm;
import com.union.mall.sms.model.query.CouponPageQuery;
import com.union.mall.sms.model.vo.CouponPageVO;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface SmsCouponService extends IService<SmsCoupon> {

    /**
     * Get coupon page list
     *
     * @param queryParams query parameters
     * @return coupon page
     */
    Page<CouponPageVO> getCouponPage(CouponPageQuery queryParams);

    /**
     * Save coupon
     *
     * @param couponForm coupon form
     * @return true if saving succeeds, false otherwise
     */
    boolean saveCoupon(CouponForm couponForm);

    /**
     * Update coupon
     *
     * @param couponId   coupon ID
     * @param couponForm coupon form
     * @return true if updating succeeds, false otherwise
     */
    boolean updateCoupon(Long couponId, CouponForm couponForm);

    /**
     * Delete coupons
     *
     * @param ids coupon IDs, separated by comma (,)
     * @return true if deletion succeeds, false otherwise
     */
    boolean deleteCoupons(String ids);

    /**
     * Get coupon form data
     *
     * @param couponId coupon ID
     * @return coupon form data
     */
    CouponForm getCouponFormData(Long couponId);
}
