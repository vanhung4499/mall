package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.model.PmsProductAttribute;
import com.hnv99.mall.model.PmsSkuStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartProduct extends PmsProduct {
    @ApiModelProperty("Product attribute list")
    private List<PmsProductAttribute> productAttributeList;
    @ApiModelProperty("Product SKU stock list")
    private List<PmsSkuStock> skuStockList;
}
