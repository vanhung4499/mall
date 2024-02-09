package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.OmsOrder;
import com.hnv99.mall.model.OmsOrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OmsOrderDetail extends OmsOrder {
    @ApiModelProperty("Order product list")
    private List<OmsOrderItem> orderItemList;
}
