package com.hnv99.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HomeFlashPromotion {
    @ApiModelProperty("Start time of the current session")
    private Date startTime;

    @ApiModelProperty("End time of the current session")
    private Date endTime;

    @ApiModelProperty("Start time of the next session")
    private Date nextStartTime;

    @ApiModelProperty("End time of the next session")
    private Date nextEndTime;

    @ApiModelProperty("Products belonging to this flash sale promotion")
    private List<FlashPromotionProduct> productList;
}
