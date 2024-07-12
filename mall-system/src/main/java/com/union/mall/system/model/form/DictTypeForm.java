package com.union.mall.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Dictionary type form object")
@Data
public class DictTypeForm {

    @Schema(description = "Dictionary type ID")
    private Long id;

    @Schema(description = "Type name")
    private String name;

    @Schema(description = "Type code")
    private String code;

    @Schema(description = "Type status (1: enabled; 0: disabled)")
    private Integer status;

    @Schema(description = "Remark")
    private String remark;

}
