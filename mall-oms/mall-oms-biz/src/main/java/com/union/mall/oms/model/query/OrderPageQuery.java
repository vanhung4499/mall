package com.union.mall.oms.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Order pagination query object")
@Data
public class OrderPageQuery extends BasePageQuery {

    /**
     * Keywords (order number / product name / member name / member phone number)
     */
    @Schema(description = "Keywords (order number / product name / member name / member phone number)")
    private String keywords;

    /**
     * Order status
     */
    @Schema(description = "Order status")
    private Integer status;

    /**
     * Start date
     */
    @Schema(description = "Start date (yyyy-MM-dd)", example = "2023-10-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00") // DateTimeFormat is used to convert query or form parameters to date type
    private Date beginDate;

    /**
     * End date
     */
    @Schema(description = "End date (yyyy-MM-dd)", example = "2025-10-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd 23:59:59")
    private Date endDate;

}
