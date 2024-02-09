package com.hnv99.mall.portal.service;

import com.hnv99.mall.common.api.CommonPage;
import com.hnv99.mall.portal.domain.ConfirmOrderResult;
import com.hnv99.mall.portal.domain.OmsOrderDetail;
import com.hnv99.mall.portal.domain.OrderParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OmsPortalOrderService {
    /**
     * Generate confirmation order information based on user shopping cart information
     */
    ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);

    /**
     * Generate an order based on submitted information
     */
    @Transactional
    Map<String, Object> generateOrder(OrderParam orderParam);

    /**
     * Callback after successful payment
     */
    @Transactional
    Integer paySuccess(Long orderId, Integer payType);

    /**
     * Automatically cancel overdue orders
     */
    @Transactional
    Integer cancelTimeOutOrder();

    /**
     * Cancel a single overdue order
     */
    @Transactional
    void cancelOrder(Long orderId);

    /**
     * Send a delayed message to cancel the order
     */
    void sendDelayMessageCancelOrder(Long orderId);

    /**
     * Confirm receipt
     */
    void confirmReceiveOrder(Long orderId);

    /**
     * Get user orders by page
     */
    CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize);

    /**
     * Get order details by order ID
     */
    OmsOrderDetail detail(Long orderId);

    /**
     * User deletes order by order ID
     */
    void deleteOrder(Long orderId);

    /**
     * Implement successful payment logic based on orderSn
     */
    @Transactional
    void paySuccessByOrderSn(String orderSn, Integer payType);
}
