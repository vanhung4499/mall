package com.union.mall.system.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Schema(description = "Permission Page Query")
public class PermPageQuery extends BasePageQuery {

    @Schema(description = "Permission name")
    private String name;

    @Schema(description = "Menu ID")
    private Long menuId;

}
