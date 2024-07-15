package com.union.mall.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Department form object")
@Data
public class DeptForm {

    @Schema(description = "Department ID")
    private Long id;

    @Schema(description = "Department name")
    private String name;

    @Schema(description = "Parent department ID")
    @NotNull(message = "Parent department ID cannot be null")
    private Long parentId;

    @Schema(description = "Status (1: enabled; 0: disabled)")
    private Integer status;

    @Schema(description = "Sorting order (smaller number ranks higher)")
    private Integer sort;

}
