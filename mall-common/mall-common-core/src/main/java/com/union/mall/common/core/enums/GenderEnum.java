package com.union.mall.common.core.enums;

import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Getter
public enum GenderEnum implements IBaseEnum<Integer> {

    MALE(1, "Male"),
    FEMALE(2, "Female"),
    UNKNOWN(0, "Unknown");

    private final Integer value;

    private final String label;

    GenderEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
