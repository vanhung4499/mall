package com.hnv99.mall.dto;

import com.hnv99.mall.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode
public class PmsProductCategoryParam {
    @ApiModelProperty("Parent category ID")
    private Long parentId;
    @NotEmpty
    @ApiModelProperty(value = "Product category name", required = true)
    private String name;
    @ApiModelProperty("Category unit")
    private String productUnit;
    @FlagValidator(value = {"0","1"}, message = "Status can only be 0 or 1")
    @ApiModelProperty("Whether to display in the navigation bar")
    private Integer navStatus;
    @FlagValidator(value = {"0","1"}, message = "Status can only be 0 or 1")
    @ApiModelProperty("Whether to display")
    private Integer showStatus;
    @Min(value = 0)
    @ApiModelProperty("Sort order")
    private Integer sort;
    @ApiModelProperty("Icon")
    private String icon;
    @ApiModelProperty("Keywords")
    private String keywords;
    @ApiModelProperty("Description")
    private String description;
    @ApiModelProperty("Product related filter attribute collection")
    private List<Long> productAttributeIdList;
}
