package com.hnv99.mall.service;

import com.hnv99.mall.dto.*;
import com.hnv99.mall.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsOrderService {
    /**
     * Query orders with pagination
     */
    List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * Batch delivery
     */
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * Batch close orders
     */
    @Transactional
    int close(List<Long> ids, String note);

    /**
     * Batch delete orders
     */
    int delete(List<Long> ids);

    /**
     * Get details of a specific order
     */
    OmsOrderDetail detail(Long id);

    /**
     * Modify order receiver information
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

    /**
     * Modify order cost information
     */
    @Transactional
    int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);

    /**
     * Modify order note
     */
    @Transactional
    int updateNote(Long id, String note, Integer status);
}
