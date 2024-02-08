package com.hnv99.mall.dto;

import com.hnv99.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
    @Getter
    @Setter
    @ApiModelProperty("Child categories")
    private List<PmsProductCategory> children;
}
