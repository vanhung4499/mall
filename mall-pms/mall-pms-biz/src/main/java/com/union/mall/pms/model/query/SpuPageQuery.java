package com.union.mall.pms.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Product paging query object")
@Data
public class SpuPageQuery extends BasePageQuery {

    @Schema(description="Keywords")
    private String keywords;

    @Schema(description="Product category ID")
    private Long categoryId;

    @Schema(description="Sorting field name")
    private String sortField;

    @Schema(description="Sorting order (asc: ascending; desc: descending)")
    private String sort;

}
