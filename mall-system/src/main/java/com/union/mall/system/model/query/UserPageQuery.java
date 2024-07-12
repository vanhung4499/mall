package com.union.mall.system.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "User paging query object")
@Data
public class UserPageQuery extends BasePageQuery {

    @Schema(description = "Keywords (username/nickname/mobile)")
    private String keywords;

    @Schema(description = "User status")
    private Integer status;

    @Schema(description = "Department ID")
    private Long deptId;

}