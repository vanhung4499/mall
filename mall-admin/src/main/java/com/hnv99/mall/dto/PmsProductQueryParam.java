package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PmsProductQueryParam {
    @ApiModelProperty("Publish status")
    private Integer publishStatus;
    @ApiModelProperty("Verification status")
    private Integer verifyStatus;
    @ApiModelProperty("Fuzzy keyword for product name")
    private String keyword;
    @ApiModelProperty("Product serial number")
    private String productSn;
    @ApiModelProperty("Product category ID")
    private Long productCategoryId;
    @ApiModelProperty("Product brand ID")
    private Long brandId;
}
