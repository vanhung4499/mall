package com.union.mall.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.form.OrderPaymentForm;
import com.union.mall.oms.model.form.OrderSubmitForm;
import com.union.mall.oms.model.query.OrderPageQuery;
import com.union.mall.oms.model.vo.OrderConfirmVO;
import com.union.mall.oms.model.vo.OrderPageVO;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface OrderService extends IService<OmsOrder> {

    /**
     * Order confirmation → Enter order creation page
     * <p>
     * Obtain details of purchased items, user's default shipping address, and anti-repetition unique token
     * There are two entry points to enter the order creation page: 1: Buy Now; 2: Shopping Cart Settlement
     *
     * @param skuId Required for direct purchase, not required for shopping cart settlement
     * @return {@link OrderConfirmVO}
     */
    OrderConfirmVO confirmOrder(Long skuId);

    /**
     * Order submission
     *
     * @param orderSubmitForm {@link OrderSubmitForm}
     * @return Order number
     */
    String submitOrder(OrderSubmitForm orderSubmitForm);

    /**
     * Order payment
     */
    <T> T payOrder(OrderPaymentForm paymentForm);

    /**
     * System closes the order
     */
    boolean closeOrder(String orderSn);

    /**
     * Delete the order
     */
    boolean deleteOrder(Long id);

    /**
     * Order pagination list
     *
     * @param queryParams Order pagination query parameters
     * @return {@link OrderPageVO}
     */
    IPage<OrderPageVO> getOrderPage(OrderPageQuery queryParams);

}
