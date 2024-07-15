package com.union.mall.oms.service.impl;

import com.union.mall.common.core.result.ResultCode;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.common.web.exception.BizException;
import com.union.mall.oms.constant.OrderConstants;
import com.union.mall.oms.converter.CartConverter;
import com.union.mall.oms.model.dto.CartItemDto;
import com.union.mall.oms.service.CartService;
import com.union.mall.pms.api.SkuFeignClient;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Shopping cart module
 * <p>
 * Core technology: BoundHashOperations
 * Data format:
 * -- key <--> Product list
 * ---- hKey:value <--> skuId Product 1
 * ---- hKey:value <--> Product 2
 * ---- hKey:value <--> Product 3
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SkuFeignClient skuFeignService;
    private final CartConverter cartConverter;

    @Override
    public List<CartItemDto> listCartItems(Long memberId) {
        if (memberId != null) {
            BoundHashOperations<String, String, CartItemDto> cartHashOperations = getCartHashOperations(memberId);
            List<CartItemDto> cartItems = new ArrayList<>(cartHashOperations.values());
            return cartItems;
        }
        return Collections.emptyList();
    }

    /**
     * Delete user's shopping cart (clear shopping cart)
     */
    @Override
    public boolean deleteCart() {
        String key = OrderConstants.MEMBER_CART_PREFIX + SecurityUtils.getMemberId();
        redisTemplate.delete(key);
        return true;
    }

    /**
     * Add item to the shopping cart
     */
    @Override
    public boolean addCartItem(Long skuId) {
        Long memberId = SecurityUtils.getMemberId();
        BoundHashOperations<String, String, CartItemDto> cartHashOperations = getCartHashOperations(memberId);
        String hKey = String.valueOf(skuId);

        CartItemDto cartItem = cartHashOperations.get(hKey);

        if (cartItem != null) {
            // Item already exists in the cart, update item count
            cartItem.setCount(cartItem.getCount() + 1); // Click "Add to Cart" once, increase count by 1
            cartItem.setChecked(true);
        } else {
            // Item does not exist in the cart, add new item to the cart
            SkuInfoDTO skuInfo = skuFeignService.getSkuInfo(skuId);
            if (skuInfo != null) {
                cartItem = cartConverter.sku2CartItem(skuInfo);
                cartItem.setCount(1);
                cartItem.setChecked(true);
            }
        }
        cartHashOperations.put(hKey, cartItem);
        return true;
    }

    /**
     * Update total number of items and selection status in the shopping cart
     */
    @Override
    public boolean updateCartItem(CartItemDto cartItem) {
        Long memberId;
        try {
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID);
        }
        BoundHashOperations<String, String, CartItemDto> cartHashOperations = getCartHashOperations(memberId);
        String hKey = String.valueOf(cartItem.getSkuId());
        CartItemDto cacheCartItem = cartHashOperations.get(hKey);
        if (cacheCartItem != null) {
            if (cartItem.getChecked() != null) {
                cacheCartItem.setChecked(cartItem.getChecked());
            }
            if (cartItem.getCount() != null) {
                cacheCartItem.setCount(cartItem.getCount());
            }
            cartHashOperations.put(hKey, cacheCartItem);
        }
        return true;
    }

    /**
     * Remove item from the shopping cart
     */
    @Override
    public boolean removeCartItem(Long skuId) {
        Long memberId;
        try {
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID);
        }
        BoundHashOperations<String, String, CartItemDto> cartHashOperations = getCartHashOperations(memberId);
        String hKey = String.valueOf(skuId);
        cartHashOperations.delete(hKey);
        return true;
    }

    /**
     * Set all items in the cart to be checked
     */
    @Override
    public boolean checkAll(boolean checked) {
        Long memberId;
        try {
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID);
        }
        BoundHashOperations<String, String, CartItemDto> cartHashOperations = getCartHashOperations(memberId);
        for (String hKey : cartHashOperations.keys()) {
            CartItemDto cartItem = cartHashOperations.get(hKey);
            cartItem.setChecked(checked);
            cartHashOperations.put(hKey, cartItem);
        }
        return true;
    }

    /**
     * Remove checked items from the shopping cart, used after payment
     */
    @Override
    public boolean removeCheckedItem() {
        Long memberId = SecurityUtils.getMemberId();
        if (memberId == null) {
            throw new BizException(ResultCode.TOKEN_INVALID);
        }
        BoundHashOperations<String, String, CartItemDto> cartHashOperations = getCartHashOperations(memberId);
        for (String hKey : cartHashOperations.keys()) {
            CartItemDto cartItem = cartHashOperations.get(hKey);
            if (cartItem != null && cartItem.getChecked()) {
                cartHashOperations.delete(hKey);
            }
        }
        return true;
    }

    /**
     * Get the first layer, i.e., the shopping cart of a user
     */
    private BoundHashOperations<String, String, CartItemDto> getCartHashOperations(Long memberId) {
        String cartKey = OrderConstants.MEMBER_CART_PREFIX + memberId;
        return redisTemplate.boundHashOps(cartKey);
    }
}
