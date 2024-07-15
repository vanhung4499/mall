package com.union.mall.sms.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Coupon Pagination Query Object")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponPageQuery extends BasePageQuery {

    @Schema(description = "Status")
    private Integer status;

    @Schema(description = "Coupon Code")
    private String code;
}
