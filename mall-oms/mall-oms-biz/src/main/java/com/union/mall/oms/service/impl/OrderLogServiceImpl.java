package com.union.mall.oms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.oms.mapper.OrderLogMapper;
import com.union.mall.oms.model.entity.OmsOrderLog;
import com.union.mall.oms.service.OrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Slf4j
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OmsOrderLog> implements OrderLogService {

    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail) {
        log.info("Adding order operation log, orderId={}, detail={}", orderId, detail);
        OmsOrderLog orderLog = new OmsOrderLog();
        orderLog.setDetail(detail);
        orderLog.setOrderId(orderId);
        orderLog.setOrderStatus(orderStatus);
        orderLog.setUser(user);
        this.save(orderLog);
    }

    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String detail) {
        Long memberId = SecurityUtils.getMemberId();
        addOrderLogs(orderId, orderStatus, memberId.toString(), detail);
    }

}
