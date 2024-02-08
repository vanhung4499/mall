package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OssCallbackParam {
    @ApiModelProperty("The callback URL of the request")
    private String callbackUrl;
    @ApiModelProperty("The parameters passed in the request during the callback")
    private String callbackBody;
    @ApiModelProperty("The format of the parameters passed during the callback, such as form submission format")
    private String callbackBodyType;
}