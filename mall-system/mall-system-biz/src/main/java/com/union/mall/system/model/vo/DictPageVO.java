package com.union.mall.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Dictionary Pagination Object")
@Data
public class DictPageVO {

    @Schema(description = "Dictionary ID")
    private Long id;

    @Schema(description = "Dictionary Name")
    private String name;

    @Schema(description = "Dictionary Value")
    private String value;

    @Schema(description = "Status (1: Enabled; 0: Disabled)")
    private Integer status;

}
