package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class PmsProductResult extends PmsProductParam {
    @Getter
    @Setter
    @ApiModelProperty("Parent ID of the selected category for the product")
    private Long cateParentId;
}
