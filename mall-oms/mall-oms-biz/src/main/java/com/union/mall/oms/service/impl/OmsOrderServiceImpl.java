package com.union.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.oms.converter.OrderConverter;
import com.union.mall.oms.mapper.OrderMapper;
import com.union.mall.oms.model.bo.OrderBO;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.query.OrderPageQuery;
import com.union.mall.oms.model.vo.OmsOrderPageVO;
import com.union.mall.oms.service.OmsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class OmsOrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements OmsOrderService {

    private final OrderConverter orderConverter;

    /**
     * Admin - Order pagination list
     *
     * @param queryParams {@link OrderPageQuery}
     * @return {@link OmsOrderPageVO}
     */
    @Override
    public IPage<OmsOrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        // Retrieve data from database using the baseMapper and convert to business object (BO)
        Page<OrderBO> boPage = this.baseMapper.getOrderPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams);

        // Convert business objects (BO) to view objects (VO) using the converter
        return orderConverter.toVoPage(boPage);
    }

}
