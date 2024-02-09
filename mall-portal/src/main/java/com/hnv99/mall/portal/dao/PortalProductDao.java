package com.hnv99.mall.portal.dao;

import com.hnv99.mall.model.SmsCoupon;
import com.hnv99.mall.portal.domain.CartProduct;
import com.hnv99.mall.portal.domain.PromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalProductDao {
    /**
     * Get shopping cart product information
     */
    CartProduct getCartProduct(@Param("id") Long id);

    /**
     * Get a list of promotional product information
     */
    List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids);

    /**
     * Get a list of available coupons
     */
    List<SmsCoupon> getAvailableCouponList(@Param("productId") Long productId, @Param("productCategoryId") Long productCategoryId);
}

