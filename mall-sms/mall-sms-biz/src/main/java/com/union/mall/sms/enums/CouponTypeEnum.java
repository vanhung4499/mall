package com.union.mall.sms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Getter
public enum CouponTypeEnum implements IBaseEnum<Integer> {

    WZ(0, null),
    MJ(1, "Full Reduction Coupon"),
    ZJ(2, "Direct Reduction Coupon"),
    ZK(3, "Discount Coupon")
    ;

    @Getter
    @EnumValue // Mybatis-Plus annotation indicating the value to insert into the database
    private Integer value;

    @Getter
    @JsonValue // Indicates the field to serialize when returning this enum
    private String label;

    CouponTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
