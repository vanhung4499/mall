package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.SmsCoupon;
import com.hnv99.mall.model.SmsCouponHistory;
import com.hnv99.mall.model.SmsCouponProductCategoryRelation;
import com.hnv99.mall.model.SmsCouponProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SmsCouponHistoryDetail extends SmsCouponHistory {
    @ApiModelProperty("Related coupon information")
    private SmsCoupon coupon;
    @ApiModelProperty("Coupon related products")
    private List<SmsCouponProductRelation> productRelationList;
    @ApiModelProperty("Coupon related product categories")
    private List<SmsCouponProductCategoryRelation> categoryRelationList;
}
