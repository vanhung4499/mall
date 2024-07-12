package com.union.mall.common.core.enums;

import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Getter
public enum StatusEnum implements IBaseEnum<Integer> {

    ENABLE(1, "Enabled"),
    DISABLE(0, "Disabled");

    private final Integer value;

    private final String label;

    StatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}

