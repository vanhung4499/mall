package com.hnv99.mall.dto;

import com.hnv99.mall.model.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "Submenu")
    private List<UmsMenuNode> children;
}
