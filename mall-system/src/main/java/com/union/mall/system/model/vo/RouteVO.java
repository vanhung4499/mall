package com.union.mall.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Route Object")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteVO {

    @Schema(description = "Route Path", example = "user")
    private String path;

    @Schema(description = "Component Path", example = "system/user/index")
    private String component;

    @Schema(description = "Redirect Link", example = "https://www.github.com")
    private String redirect;

    @Schema(description = "Route Name")
    private String name;

    @Schema(description = "Route Meta Attributes")
    private Meta meta;

    @Schema(description = "Route Meta Attributes Type")
    @Data
    public static class Meta {

        @Schema(description = "Route Title")
        private String title;

        @Schema(description = "Icon")
        private String icon;

        @Schema(description = "Whether Hidden (true-yes false-no)", example = "true")
        private Boolean hidden;

        @Schema(description = "Roles with Route Permissions", example = "['ADMIN','ROOT']")
        private List<String> roles;

        @Schema(description = "Whether to Enable Page Cache for Menu", example = "true")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean keepAlive;

        @Schema(description = "Whether to Always Display a Single Child Route for Directory", example = "true")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean alwaysShow;
    }

    @Schema(description = "List of Child Routes")
    private List<RouteVO> children;
}
