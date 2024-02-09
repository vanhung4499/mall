package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.model.PmsProductFullReduction;
import com.hnv99.mall.model.PmsProductLadder;
import com.hnv99.mall.model.PmsSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PromotionProduct extends PmsProduct {
    // Product stock information
    private List<PmsSkuStock> skuStockList;
    // Product discount information
    private List<PmsProductLadder> productLadderList;
    // Product full reduction information
    private List<PmsProductFullReduction> productFullReductionList;
}
