package com.union.mall.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Role form object")
@Data
public class RoleForm {

    @Schema(description = "Role ID")
    private Long id;

    @Schema(description = "Role name")
    @NotBlank(message = "Role name cannot be empty")
    private String name;

    @Schema(description = "Role code")
    @NotBlank(message = "Role code cannot be empty")
    private String code;

    @Schema(description = "Sort order")
    private Integer sort;

    @Schema(description = "Role status (1: Active; 0: Disabled)")
    private Integer status;

    @Schema(description = "Data scope")
    private Integer dataScope;

}
