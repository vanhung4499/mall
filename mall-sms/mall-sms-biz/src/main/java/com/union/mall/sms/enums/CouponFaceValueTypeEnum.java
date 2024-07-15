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
public enum CouponFaceValueTypeEnum implements IBaseEnum<Integer> {

    CASH(1, "Cash"),
    DISCOUNT(2, "Discount"),
    ;

    @Getter
    @EnumValue // Mybatis-Plus annotation indicating the value to insert into the database
    private Integer value;

    @Getter
    @JsonValue // Indicates the field to serialize when returning this enum
    private String label;

    CouponFaceValueTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
