package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateAdminPasswordParam {
    @NotEmpty
    @ApiModelProperty(value = "Username", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "Old password", required = true)
    private String oldPassword;
    @NotEmpty
    @ApiModelProperty(value = "New password", required = true)
    private String newPassword;
}
