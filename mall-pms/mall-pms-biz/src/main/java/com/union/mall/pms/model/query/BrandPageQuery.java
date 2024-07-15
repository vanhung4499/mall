package com.union.mall.pms.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Brand paging query object")
@Data
public class BrandPageQuery extends BasePageQuery {

    @Schema(description="Keywords")
    private String keywords;

}
