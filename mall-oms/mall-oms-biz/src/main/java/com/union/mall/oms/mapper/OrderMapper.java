package com.union.mall.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.oms.model.bo.OrderBO;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.query.OrderPageQuery;
import org.apache.ibatis.annotations.Mapper;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface OrderMapper extends BaseMapper<OmsOrder> {

    /**
     * Order pagination list
     *
     * @param page         Pagination information
     * @param queryParams  Query parameters for filtering orders
     * @return             Page of OrderBO containing orders
     */
    Page<OrderBO> getOrderPage(Page<OrderBO> page, OrderPageQuery queryParams);
}
