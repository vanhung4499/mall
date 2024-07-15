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
public enum ValidityPeriodTypeEnum implements IBaseEnum<Integer> {

    UNKNOWN(0, null),
    DATE_RANGE(1, "Date Range"),
    FIXED_DAYS(2, "Fixed Days"),
    ;

    @Getter
    @EnumValue // Mybatis-Plus annotation indicating the value to insert into the database
    private Integer value;

    @Getter
    @JsonValue // Indicates the field to serialize when returning this enum
    private String label;

    ValidityPeriodTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
