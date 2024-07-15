package com.union.mall.sms.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Coupon Form Object")
@Data
public class CouponForm {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "Coupon Name")
    private String name;

    @Schema(description = "Coupon Type (1: Full Reduction; 2: Direct Reduction; 3: Discount)")
    private Integer type;

    @Schema(description = "Face Value Type (1: Amount; 2: Discount)")
    private Integer faceValueType;

    @Schema(description = "Face Value Amount (unit: cents)")
    private Long faceValue;

    @Schema(description = "Coupon Discount")
    private BigDecimal discount;

    @Schema(description = "Coupon Code")
    private String code;

    @Schema(description = "Platform (0: All Platforms; 1: Mobile; 2: PC)")
    private Integer platform;

    @Schema(description = "Circulation (-1: Unlimited)")
    private Integer circulation;

    @Schema(description = "Usage Threshold (0: No Threshold)")
    private Long minPoint;

    @Schema(description = "Per Person Limit (-1: No Limit)")
    private Integer perLimit;

    @Schema(description = "Validity Period Type (1: Date Range; 2: Fixed Days)")
    private Integer validityPeriodType;

    @Schema(description = "Validity Days from Receipt")
    private Integer validityDays;

    @Schema(description = "Validity Start Time")
    private Date validityBeginTime;

    @Schema(description = "Validity End Time")
    private Date validityEndTime;

    @Schema(description = "Application Scope (0: General; 1: Specific Category; 2: Specific Product)")
    private Integer applicationScope;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Applicable Product Category IDs for the Coupon")
    private List<Long> spuCategoryIds;

    @Schema(description = "Applicable Product List for the Coupon")
    private List<Long> spuIds;
}
