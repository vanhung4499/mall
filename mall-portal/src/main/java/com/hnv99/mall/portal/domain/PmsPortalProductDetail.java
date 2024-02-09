package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsPortalProductDetail{
    @ApiModelProperty("Product information")
    private PmsProduct product;
    @ApiModelProperty("Product brand")
    private PmsBrand brand;
    @ApiModelProperty("Product attributes and parameters")
    private List<PmsProductAttribute> productAttributeList;
    @ApiModelProperty("Manually entered product attributes and parameter values")
    private List<PmsProductAttributeValue> productAttributeValueList;
    @ApiModelProperty("Product's sku stock information")
    private List<PmsSkuStock> skuStockList;
    @ApiModelProperty("Product ladder price settings")
    private List<PmsProductLadder> productLadderList;
    @ApiModelProperty("Product full reduction price settings")
    private List<PmsProductFullReduction> productFullReductionList;
    @ApiModelProperty("Available coupons for the product")
    private List<SmsCoupon> couponList;
}
