package com.hnv99.mall.dto;

import com.hnv99.mall.model.OmsOrder;
import com.hnv99.mall.model.OmsOrderItem;
import com.hnv99.mall.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OmsOrderDetail extends OmsOrder {
    @ApiModelProperty("Order item list")
    private List<OmsOrderItem> orderItemList;

    @ApiModelProperty("Order operation record list")
    private List<OmsOrderOperateHistory> historyList;
}

