package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MinioUploadDto {
    @ApiModelProperty("File Access URL")
    private String url;
    @ApiModelProperty("File Name")
    private String name;
}

