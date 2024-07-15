package com.union.mall.system.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Dictionary Type Page Query Object")
@Data
public class DictTypePageQuery extends BasePageQuery {

    @Schema(description = "Keywords (type name/type code)")
    private String keywords;

}
