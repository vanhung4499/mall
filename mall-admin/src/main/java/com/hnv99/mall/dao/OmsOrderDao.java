package com.hnv99.mall.dao;

import com.hnv99.mall.dto.OmsOrderDeliveryParam;
import com.hnv99.mall.dto.OmsOrderDetail;
import com.hnv99.mall.dto.OmsOrderQueryParam;
import com.hnv99.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderDao {
    /**
     * Query orders by condition
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);

    /**
     * Batch delivery
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * Get order details
     */
    OmsOrderDetail getDetail(@Param("id") Long id);
}
