package com.union.mall.oms.model.dto;

import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.entity.OmsOrderItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */

@Data
@Accessors(chain = true)
public class OrderDTO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

}
