package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsReturnApplyQueryParam {
    @ApiModelProperty("Service order number")
    private Long id;
    @ApiModelProperty(value = "Recipient's name/number")
    private String receiverKeyword;
    @ApiModelProperty(value = "Application status: 0->Pending; 1->Returning; 2->Completed; 3->Rejected")
    private Integer status;
    @ApiModelProperty(value = "Application time")
    private String createTime;
    @ApiModelProperty(value = "Handler")
    private String handleMan;
    @ApiModelProperty(value = "Handling time")
    private String handleTime;
}
