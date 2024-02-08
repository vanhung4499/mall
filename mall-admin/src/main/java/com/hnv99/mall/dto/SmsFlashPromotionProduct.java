package com.hnv99.mall.dto;

import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.model.SmsFlashPromotionProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation {
    @Getter
    @Setter
    @ApiModelProperty("Associated product")
    private PmsProduct product;
}
