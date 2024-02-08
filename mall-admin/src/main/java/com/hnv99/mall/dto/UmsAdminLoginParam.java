package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode
public class UmsAdminLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "Username",required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "Password",required = true)
    private String password;
}
