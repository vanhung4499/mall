package com.union.mall.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.query.OrderPageQuery;
import com.union.mall.oms.model.vo.OmsOrderPageVO;


/**
 * Admin-Order business interface
 *
 * @author vanhung4499
 */
public interface OmsOrderService extends IService<OmsOrder> {

    /**
     * Order pagination list
     *
     * @param queryParams {@link OrderPageQuery}
     * @return
     */
    IPage<OmsOrderPageVO> getOrderPage(OrderPageQuery queryParams);

}
