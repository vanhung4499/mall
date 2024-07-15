package com.union.mall.oms.service;

import com.union.mall.oms.model.dto.CartItemDto;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface CartService {

    List<CartItemDto> listCartItems(Long memberId);

    boolean deleteCart();

    boolean addCartItem(Long skuId);

    boolean updateCartItem(CartItemDto cartItem);

    boolean removeCartItem(Long skuId);

    boolean removeCheckedItem();

    boolean checkAll(boolean checked);

}
