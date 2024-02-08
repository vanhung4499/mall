package com.hnv99.mall.dto;

import com.hnv99.mall.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode
public class PmsProductAttributeParam {
    @NotEmpty
    @ApiModelProperty("Attribute category ID")
    private Long productAttributeCategoryId;

    @NotEmpty
    @ApiModelProperty("Attribute name")
    private String name;

    @FlagValidator({"0","1","2"})
    @ApiModelProperty("Attribute selection type: 0->unique; 1->single select; 2->multi select")
    private Integer selectType;

    @FlagValidator({"0","1"})
    @ApiModelProperty("Attribute input method: 0->manual input; 1->select from list")
    private Integer inputType;

    @ApiModelProperty("Optional value list, separated by commas")
    private String inputList;

    private Integer sort;

    @FlagValidator({"0","1"})
    @ApiModelProperty("Category filter style: 0->normal; 1->color")
    private Integer filterType;

    @FlagValidator({"0","1","2"})
    @ApiModelProperty("Search type; 0->no need to search; 1->keyword search; 2->range search")
    private Integer searchType;

    @FlagValidator({"0","1"})
    @ApiModelProperty("Whether products with the same attributes are related; 0->not related; 1->related")
    private Integer relatedStatus;

    @FlagValidator({"0","1"})
    @ApiModelProperty("Whether manual addition is supported; 0->not supported; 1->supported")
    private Integer handAddStatus;

    @FlagValidator({"0","1"})
    @ApiModelProperty("Type of the attribute; 0->specification; 1->parameter")
    private Integer type;
}
