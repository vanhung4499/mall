package com.hnv99.mall.dto;

import com.hnv99.mall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class PmsProductParam extends PmsProduct {
    @ApiModelProperty("Product ladder price settings")
    private List<PmsProductLadder> productLadderList;
    @ApiModelProperty("Product full reduction price settings")
    private List<PmsProductFullReduction> productFullReductionList;
    @ApiModelProperty("Product member price settings")
    private List<PmsMemberPrice> memberPriceList;
    @ApiModelProperty("Product's SKU stock information")
    private List<PmsSkuStock> skuStockList;
    @ApiModelProperty("Product parameters and custom attribute values")
    private List<PmsProductAttributeValue> productAttributeValueList;
    @ApiModelProperty("Subject and product relation")
    private List<CmsSubjectProductRelation> subjectProductRelationList;
    @ApiModelProperty("Preference area and product relation")
    private List<CmsPreferenceAreaProductRelation> prefrenceAreaProductRelationList;
}
