package com.hnv99.mall.portal.domain;

import com.hnv99.mall.model.CmsSubject;
import com.hnv99.mall.model.PmsBrand;
import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.model.SmsHomeAdvertise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeContentResult {
    @ApiModelProperty("Carousel advertisement")
    private List<SmsHomeAdvertise> advertiseList;
    @ApiModelProperty("Recommended brands")
    private List<PmsBrand> brandList;
    @ApiModelProperty("Current flash sale session")
    private HomeFlashPromotion homeFlashPromotion;
    @ApiModelProperty("New product recommendations")
    private List<PmsProduct> newProductList;
    @ApiModelProperty("Popular product recommendations")
    private List<PmsProduct> hotProductList;
    @ApiModelProperty("Recommended subjects")
    private List<CmsSubject> subjectList;
}
