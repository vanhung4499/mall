package com.union.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.oms.model.form.OrderPaymentForm;
import com.union.mall.oms.model.form.OrderSubmitForm;
import com.union.mall.oms.model.query.OrderPageQuery;
import com.union.mall.oms.model.vo.OrderConfirmVO;
import com.union.mall.oms.model.vo.OrderPageVO;
import com.union.mall.oms.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The system automatically cancels the listener if the order times out and is not paid.
 *
 * @author vanhung4499
 */
@Tag(name = "APP-Order API")
@RestController
@RequestMapping("/app-api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Retrieves a paginated list of orders.
     *
     * @param queryParams the query parameters for pagination and filtering
     * @return a paginated result of order data
     */
    @Operation(summary = "Order Pagination List")
    @GetMapping
    public PageResult<OrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        IPage<OrderPageVO> result = orderService.getOrderPage(queryParams);
        return PageResult.success(result);
    }

    /**
     * Confirms the order. This can be accessed via two routes:
     * 1. Immediate purchase
     * 2. Shopping cart checkout
     *
     * @param skuId SKU ID required for immediate purchase, not needed for cart checkout
     * @return the order confirmation data
     */
    @Operation(summary = "Order Confirmation", description = "There are two ways to enter the order confirmation page: 1. Immediate purchase; 2. Shopping cart checkout")
    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirmOrder(
            @Parameter(name = "Required for immediate purchase, not needed for cart checkout") @RequestParam(required = false) Long skuId
    ) {
        OrderConfirmVO result = orderService.confirmOrder(skuId);
        return Result.success(result);
    }

    /**
     * Submits the order.
     *
     * @param submitForm the form containing order submission details
     * @return the order number of the submitted order
     */
    @Operation(summary = "Order Submission")
    @PostMapping("/submit")
    public Result<String> submitOrder(@Validated @RequestBody OrderSubmitForm submitForm) {
        String orderSn = orderService.submitOrder(submitForm);
        return Result.success(orderSn);
    }

    /**
     * Processes the order payment.
     *
     * @param paymentForm the form containing payment details
     * @return a result indicating the success or failure of the payment
     */
    @Operation(summary = "Order Payment")
    @PostMapping("/payment")
    public Result payOrder(@Validated @RequestBody OrderPaymentForm paymentForm) {
        boolean result = orderService.payOrder(paymentForm);
        return Result.judge(result);
    }

    /**
     * Deletes an order.
     *
     * @param orderId the ID of the order to be deleted
     * @return a result indicating the success or failure of the deletion
     */
    @Operation(summary = "Order Deletion")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        return Result.judge(deleted);
    }
}
