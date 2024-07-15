package com.union.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.oms.model.entity.OmsOrderLog;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface OrderLogService extends IService<OmsOrderLog> {

    /**
     * Add order operation log record
     * @param orderId Order ID
     * @param orderStatus Order status
     * @param user Operator
     * @param detail Description
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail);

    /**
     * Add order operation log record without user information
     * @param orderId Order ID
     * @param orderStatus Order status
     * @param detail Description
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String detail);
}
