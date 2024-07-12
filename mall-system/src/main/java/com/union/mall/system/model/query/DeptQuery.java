package com.union.mall.system.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Department Paging Query Object")
@Data
public class DeptQuery {

    @Schema(description = "Keywords (department name)")
    private String keywords;

    @Schema(description = "Status (1->Enabled; 0->Disabled)")
    private Integer status;

}
