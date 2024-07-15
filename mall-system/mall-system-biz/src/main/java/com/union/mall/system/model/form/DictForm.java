package com.union.mall.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Dictionary form object")
@Data
public class DictForm {

    @Schema(description = "Dictionary ID")
    private Long id;

    @Schema(description = "Type code")
    private String typeCode;

    @Schema(description = "Dictionary name")
    private String name;

    @Schema(description = "Dictionary value")
    private String value;

    @Schema(description = "Status (1: enabled; 0: disabled)")
    private Integer status;

    @Schema(description = "Sorting order")
    private Integer sort;

    @Schema(description = "Dictionary remark")
    private String remark;

}
