package com.union.mall.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Dictionary Type Pagination Object")
@Data
public class DictTypePageVO {

    @Schema(description = "Dictionary Type ID")
    private Long id;

    @Schema(description = "Type Name")
    private String name;

    @Schema(description = "Type Code")
    private String code;

    @Schema(description = "Status: 1 - Enabled; 0 - Disabled")
    private Integer status;

}
