package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsProductCategoryNode extends PmsProductCategory {
    @ApiModelProperty("Collection of subcategories")
    private List<PmsProductCategoryNode> children;
}
