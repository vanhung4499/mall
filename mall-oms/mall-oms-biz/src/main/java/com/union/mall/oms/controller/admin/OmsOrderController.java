package com.union.mall.oms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.oms.model.dto.OrderDTO;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.entity.OmsOrderItem;
import com.union.mall.oms.model.query.OrderPageQuery;
import com.union.mall.oms.model.vo.OmsOrderPageVO;
import com.union.mall.oms.service.OmsOrderService;
import com.union.mall.oms.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Admin Order Controller
 *
 * Manages administrative functions related to orders.
 * Provides endpoints for fetching orders and order details.
 *
 * @author vanhung4499
 */
@Tag(name = "Admin Order Management")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OmsOrderController {

    private final OmsOrderService orderService;
    private final OrderItemService orderItemService;

    /**
     * Retrieves a paginated list of orders.
     *
     * @param queryParams Query parameters for pagination and filtering
     * @return PageResult containing a list of order summaries
     */
    @Operation(summary = "Order Pagination List")
    @GetMapping
    public PageResult<OmsOrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        IPage<OmsOrderPageVO> page = orderService.getOrderPage(queryParams);
        return PageResult.success(page);
    }

    /**
     * Retrieves details of a specific order.
     *
     * @param orderId ID of the order to fetch details for
     * @return Result containing the order details and items
     */
    @Operation(summary = "Order Details")
    @GetMapping("/{orderId}")
    public Result getOrderDetail(
            @Parameter(name = "Order ID") @PathVariable Long orderId
    ) {
        OrderDTO orderDTO = new OrderDTO();

        // Fetch order details
        OmsOrder order = orderService.getById(orderId);

        // Fetch order items
        List<OmsOrderItem> orderItems = orderItemService.list(new LambdaQueryWrapper<OmsOrderItem>()
                .eq(OmsOrderItem::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(Collections.emptyList());

        orderDTO.setOrder(order).setOrderItems(orderItems);
        return Result.success(orderDTO);
    }
}
