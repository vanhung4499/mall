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
public enum CouponApplicationScopeEnum implements IBaseEnum<Integer> {

    ALL(0, "Applicable to all"),
    /**
     * Specific product categories
     */
    SPU_CATEGORY(1, "Applicable to specific product categories"),
    SPU(2, "Applicable to specific products"),
    ;

    @Getter
    @EnumValue // Mybatis-Plus annotation indicating the value to insert into the database
    private Integer value;

    @Getter
    @JsonValue // Indicates the field to serialize when returning this enum
    private String label;

    CouponApplicationScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
