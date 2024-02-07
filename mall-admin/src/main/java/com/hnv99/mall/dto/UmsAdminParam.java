package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UmsAdminParam {
    @NotEmpty
    @ApiModelProperty(value = "Username", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "Password", required = true)
    private String password;
    @ApiModelProperty(value = "User Avatar")
    private String icon;
    @Email
    @ApiModelProperty(value = "Email")
    private String email;
    @ApiModelProperty(value = "User Nickname")
    private String nickName;
    @ApiModelProperty(value = "Note")
    private String note;
}
