package com.union.mall.system.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Dictionary Item Page Query Object")
@Data
public class DictPageQuery extends BasePageQuery {

    @Schema(description = "Keywords (dictionary item name)")
    private String keywords;

    @Schema(description = "Dictionary type code")
    private String typeCode;
}
