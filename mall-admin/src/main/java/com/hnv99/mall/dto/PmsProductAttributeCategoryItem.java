package com.hnv99.mall.dto;

import com.hnv99.mall.model.PmsProductAttribute;
import com.hnv99.mall.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    @Getter
    @Setter
    @ApiModelProperty(value = "Product attribute list")
    private List<PmsProductAttribute> productAttributeList;
}

