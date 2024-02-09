package com.hnv99.mall.portal.dao;

import com.hnv99.mall.model.OmsOrderItem;
import com.hnv99.mall.portal.domain.OmsOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalOrderDao {

    /**
     * Get order details and ordered product details
     */
    OmsOrderDetail getDetail(@Param("orderId") Long orderId);

    /**
     * Update the locked stock and actual stock in the pms_sku_stock table
     */
    int updateSkuStock(@Param("itemList") List<OmsOrderItem> orderItemList);

    /**
     * Get orders that have exceeded the timeout period
     * @param minute timeout period (in minutes)
     */
    List<OmsOrderDetail> getTimeOutOrders(@Param("minute") Integer minute);

    /**
     * Update the status of multiple orders
     */
    int updateOrderStatus(@Param("ids") List<Long> ids,@Param("status") Integer status);

    /**
     * Release the stock lock of cancelled orders
     */
    int releaseSkuStockLock(@Param("itemList") List<OmsOrderItem> orderItemList);

}
