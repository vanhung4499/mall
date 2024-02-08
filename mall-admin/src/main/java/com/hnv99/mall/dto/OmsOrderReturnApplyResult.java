package com.hnv99.mall.dto;

import com.hnv99.mall.model.OmsCompanyAddress;
import com.hnv99.mall.model.OmsOrderReturnApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {
    @ApiModelProperty(value = "Company receiving address")
    private OmsCompanyAddress companyAddress;
}
