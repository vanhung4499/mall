package com.union.mall.system.model.form;

import com.union.mall.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Menu form object")
@Data
public class MenuForm {

    @Schema(description = "Menu ID")
    private Long id;

    @Schema(description = "Parent menu ID")
    private Long parentId;

    @Schema(description = "Menu name")
    private String name;

    @Schema(description = "Menu type (1-Menu; 2-Directory; 3-External link; 4-Button permission)")
    private MenuTypeEnum type;

    @Schema(description = "Route path")
    private String path;

    @Schema(description = "Component path (full path of Vue page, omitting the .vue suffix)")
    private String component;

    @Schema(description = "Permission identifier")
    private String perm;

    @Schema(description = "Display status (1: Display; 0: Hide)")
    private Integer visible;

    @Schema(description = "Sort order (Smaller number has higher priority)")
    private Integer sort;

    @Schema(description = "Menu icon")
    private String icon;

    @Schema(description = "Redirect path")
    private String redirect;

    @Schema(description = "[Menu] Whether to enable page caching", example = "1")
    private Integer keepAlive;

    @Schema(description = "[Directory] Whether to always show when it has only one child route", example = "1")
    private Integer alwaysShow;

}
