package com.hnv99.mall.dto;

import com.hnv99.mall.model.SmsCoupon;
import com.hnv99.mall.model.SmsCouponProductCategoryRelation;
import com.hnv99.mall.model.SmsCouponProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SmsCouponParam extends SmsCoupon {
    @Getter
    @Setter
    @ApiModelProperty("Products bound to the coupon")
    private List<SmsCouponProductRelation> productRelationList;

    @Getter
    @Setter
    @ApiModelProperty("Product categories bound to the coupon")
    private List<SmsCouponProductCategoryRelation> productCategoryRelationList;
}
