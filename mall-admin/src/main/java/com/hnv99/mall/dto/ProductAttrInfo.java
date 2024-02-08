package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ProductAttrInfo {
    @ApiModelProperty("Product attribute ID")
    private Long attributeId;

    @ApiModelProperty("Product attribute category ID")
    private Long attributeCategoryId;
}
