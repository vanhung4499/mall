package com.union.mall.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.union.mall.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Menu View Object")
@Data
public class MenuVO {

    @Schema(description = "Menu ID")
    private Long id;

    @Schema(description = "Parent Menu ID")
    private Long parentId;

    @Schema(description = "Menu Name")
    private String name;

    @Schema(description = "Menu Type")
    private MenuTypeEnum type;

    @Schema(description = "Route Path")
    private String path;

    @Schema(description = "Component Path")
    private String component;

    @Schema(description = "Menu Sorting (Smaller number means higher priority)")
    private Integer sort;

    @Schema(description = "Menu Visibility (1: visible; 0: hidden)")
    private Integer visible;

    @Schema(description = "Icon")
    private String icon;

    @Schema(description = "Redirect Path")
    private String redirect;

    @Schema(description = "Button Permission")
    private String perm;

    @Schema(description = "Child Menus")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuVO> children;

}
