package com.union.mall.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Menu Page Query")
@Data
public class MenuQuery {

    @Schema(description = "Keywords (menu name)")
    private String keywords;

    @Schema(description = "Status (1->display; 0->hidden)")
    private Integer status;

}
