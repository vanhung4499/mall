package com.hnv99.mall.dto;

import com.hnv99.mall.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode
public class PmsBrandParam {
    @NotEmpty
    @ApiModelProperty(value = "Brand name", required = true)
    private String name;
    @ApiModelProperty(value = "First letter of brand")
    private String firstLetter;
    @Min(value = 0)
    @ApiModelProperty(value = "Sort field")
    private Integer sort;
    @FlagValidator(value = {"0","1"}, message = "Manufacturer status is incorrect")
    @ApiModelProperty(value = "Is it a manufacturer")
    private Integer factoryStatus;
    @FlagValidator(value = {"0","1"}, message = "Display status is incorrect")
    @ApiModelProperty(value = "Is it displayed")
    private Integer showStatus;
    @NotEmpty
    @ApiModelProperty(value = "Brand logo", required = true)
    private String logo;
    @ApiModelProperty(value = "Brand big picture")
    private String bigPic;
    @ApiModelProperty(value = "Brand story")
    private String brandStory;
}
