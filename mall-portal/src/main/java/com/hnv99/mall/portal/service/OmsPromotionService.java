package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.OmsCartItem;
import com.hnv99.mall.portal.domain.CartPromotionItem;

import java.util.List;

public interface OmsPromotionService {
    /**
     * Calculate promotional activity information in the shopping cart
     * @param cartItemList Shopping cart
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
