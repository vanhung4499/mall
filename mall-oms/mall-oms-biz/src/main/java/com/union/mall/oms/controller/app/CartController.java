package com.union.mall.oms.controller.app;

import com.union.mall.common.core.result.Result;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.oms.model.dto.CartItemDto;
import com.union.mall.oms.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "App Shopping Cart API")
@RestController
@RequestMapping("/app-api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Retrieves the shopping cart for the current user.
     *
     * @return Result containing a list of cart items
     */
    @Operation(summary = "Get Shopping Cart")
    @GetMapping
    public <T> Result<T> getCart() {
        List<CartItemDto> result = cartService.listCartItems(SecurityUtils.getMemberId());
        return Result.success((T) result);
    }

    /**
     * Deletes the shopping cart for the current user.
     *
     * @return Result indicating success or failure
     */
    @Operation(summary = "Delete Shopping Cart")
    @DeleteMapping
    public <T> Result<T> deleteCart() {
        boolean result = cartService.deleteCart();
        return Result.judge(result);
    }

    /**
     * Adds an item to the shopping cart.
     *
     * @param skuId ID of the SKU to add
     * @return Result indicating success or failure
     */
    @Operation(summary = "Add Item to Cart")
    @PostMapping
    public <T> Result<T> addCartItem(@RequestParam Long skuId) {
        cartService.addCartItem(skuId);
        return Result.success();
    }

    /**
     * Updates an item in the shopping cart.
     *
     * @param skuId ID of the SKU to update
     * @param cartItem Details of the cart item to update
     * @return Result indicating success or failure
     */
    @Operation(summary = "Update Cart Item")
    @PutMapping("/skuId/{skuId}")
    public <T> Result<T> updateCartItem(
            @PathVariable Long skuId,
            @RequestBody CartItemDto cartItem
    ) {
        cartItem.setSkuId(skuId);
        boolean result = cartService.updateCartItem(cartItem);
        return Result.judge(result);
    }

    /**
     * Removes an item from the shopping cart.
     *
     * @param skuId ID of the SKU to remove
     * @return Result indicating success or failure
     */
    @Operation(summary = "Remove Cart Item")
    @DeleteMapping("/skuId/{skuId}")
    public <T> Result<T> removeCartItem(@PathVariable Long skuId) {
        boolean result = cartService.removeCartItem(skuId);
        return Result.judge(result);
    }

    /**
     * Selects or deselects all items in the shopping cart.
     *
     * @param checked Whether to select or deselect all items
     * @return Result indicating success or failure
     */
    @Operation(summary = "Check/Uncheck All Cart Items")
    @PatchMapping("/_check")
    public <T> Result<T> check(
            @Parameter(name = "Select/Deselect All") boolean checked
    ) {
        boolean result = cartService.checkAll(checked);
        return Result.judge(result);
    }
}
