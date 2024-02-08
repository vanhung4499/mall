package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsUpdateStatusParam {
    @ApiModelProperty("Service order number")
    private Long id;
    @ApiModelProperty("Related receiving address ID")
    private Long companyAddressId;
    @ApiModelProperty("Confirmed refund amount")
    private BigDecimal returnAmount;
    @ApiModelProperty("Processing notes")
    private String handleNote;
    @ApiModelProperty("Handler")
    private String handleMan;
    @ApiModelProperty("Receiving notes")
    private String receiveNote;
    @ApiModelProperty("Receiver")
    private String receiveMan;
    @ApiModelProperty("Application status: 1->Returning; 2->Completed; 3->Rejected")
    private Integer status;
}
