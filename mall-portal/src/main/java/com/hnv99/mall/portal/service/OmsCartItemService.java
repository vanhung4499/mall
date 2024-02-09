package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.OmsCartItem;
import com.hnv99.mall.portal.domain.CartProduct;
import com.hnv99.mall.portal.domain.CartPromotionItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsCartItemService {
    /**
     * Check if the shopping cart contains the product. If yes, increase the quantity, if not, add it to the cart.
     */
    @Transactional
    int add(OmsCartItem cartItem);

    /**
     * Get the shopping cart list based on the member's ID.
     */
    List<OmsCartItem> list(Long memberId);

    /**
     * Get the shopping cart list that includes promotional activity information.
     */
    List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds);

    /**
     * Modify the quantity of a certain product in the shopping cart.
     */
    int updateQuantity(Long id, Long memberId, Integer quantity);

    /**
     * Batch delete products in the shopping cart.
     */
    int delete(Long memberId,List<Long> ids);

    /**
     * Get product information from the shopping cart for selecting product specifications.
     */
    CartProduct getCartProduct(Long productId);

    /**
     * Modify the specifications of the product in the shopping cart.
     */
    @Transactional
    int updateAttr(OmsCartItem cartItem);

    /**
     * Clear the shopping cart.
     */
    int clear(Long memberId);
}
