package com.hnv99.mall.demo.dto;

import com.hnv99.mall.demo.validator.FlagValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "PmsBrandDto") // Model annotation for Swagger
public class PmsBrandDto {
    @ApiModelProperty(value = "Brand name", required = true) // Field annotation for Swagger
    @NotNull(message = "Name cannot be null") // Validation annotation
    private String name;

    @ApiModelProperty(value = "First letter of the brand", required = true)
    @NotNull(message = "First letter cannot be null")
    private String firstLetter;

    @ApiModelProperty(value = "Sorting field")
    @Min(value = 0, message = "Sorting minimum is 0")
    private Integer sort;

    @ApiModelProperty(value = "Whether it is a manufacturer")
    @FlagValidator(value = {"0","1"}, message = "Manufacturer status is incorrect")
    private Integer factoryStatus;

    @ApiModelProperty(value = "Whether to display")
    @FlagValidator(value = {"0","1"}, message = "Display status is incorrect")
    private Integer showStatus;

    @ApiModelProperty(value = "Brand logo")
    private String logo;

    @ApiModelProperty(value = "Brand large picture")
    private String bigPic;

    @ApiModelProperty(value = "Brand story")
    private String brandStory;
}
