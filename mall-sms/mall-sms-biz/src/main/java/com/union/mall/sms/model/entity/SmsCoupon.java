package com.union.mall.sms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@TableName(value = "sms_coupon")
@Data
public class SmsCoupon extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Coupon Type (1: Full Reduction Coupon; 2: Direct Reduction Coupon; 3: Discount Coupon)
     */
    private Integer type;

    /**
     * Coupon Name
     */
    private String name;

    /**
     * Coupon Code
     */
    private String code;

    /**
     * Platform (0: All Platforms; 1: Mobile; 2: PC)
     */
    private Integer platform;

    /**
     * Coupon Face Value Type (1: Amount; 2: Discount)
     */
    private Integer faceValueType;

    /**
     * Coupon Face Value Amount (in cents)
     */
    private Long faceValue;

    /**
     * Coupon Discount
     */
    private BigDecimal discount;

    /**
     * Minimum Threshold for Use (0: No Threshold)
     */
    private Long minPoint;

    /**
     * Maximum Number of Coupons per Person (0: No Limit)
     */
    private Integer perLimit;

    /**
     * Validity Period Type (1: Valid Days from Receipt; 2: Specific Start and End Dates)
     */
    private Integer validityPeriodType;

    /**
     * Number of Valid Days from Receipt
     */
    private Integer validityDays;

    /**
     * Validity Start Time
     */
    private Date validityBeginTime;

    /**
     * Validity End Time
     */
    private Date validityEndTime;

    /**
     * Application Scope (0: Universal; 1: Specific Product Category; 2: Specific Product)
     */
    private Integer applicationScope;

    /**
     * Circulation (-1: Unlimited)
     */
    private Integer circulation;

    /**
     * Number of Coupons Received (Statistics)
     */
    private Integer receivedCount;

    /**
     * Number of Coupons Used (Statistics)
     */
    private Integer usedCount;

    /**
     * Remark
     */
    private String remark;

    /**
     * Logical Deletion Flag (0: Normal; 1: Deleted)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

}
