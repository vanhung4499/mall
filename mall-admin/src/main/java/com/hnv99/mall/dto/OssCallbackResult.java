package com.hnv99.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OssCallbackResult {
    @ApiModelProperty("File name")
    private String filename;
    @ApiModelProperty("File size")
    private String size;
    @ApiModelProperty("File's mimeType")
    private String mimeType;
    @ApiModelProperty("Width of the image file")
    private String width;
    @ApiModelProperty("Height of the image file")
    private String height;
}
