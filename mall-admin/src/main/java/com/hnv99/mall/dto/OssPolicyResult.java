package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OssPolicyResult {
    @ApiModelProperty("User identification used in access authentication")
    private String accessKeyId;
    @ApiModelProperty("User form upload policy, a base64 encoded string")
    private String policy;
    @ApiModelProperty("String after policy signature")
    private String signature;
    @ApiModelProperty("Prefix of the upload folder path")
    private String dir;
    @ApiModelProperty("Access domain name of the oss external service")
    private String host;
    @ApiModelProperty("Callback settings after successful upload")
    private String callback;
}
