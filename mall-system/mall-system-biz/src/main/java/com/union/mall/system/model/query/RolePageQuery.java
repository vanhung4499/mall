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
public class RolePageQuery extends BasePageQuery {

    @Schema(description = "Keywords (role name/role code)")
    private String keywords;
}
