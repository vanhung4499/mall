package com.union.mall.common.mybatis.enums;

import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * Data scope enumeration.
 * <p>
 * Defines different levels of data scope permissions.
 *
 * @author vanhung4499
 */
public enum DataScopeEnum implements IBaseEnum<Integer> {

    /**
     * The smaller the value, the broader the data scope.
     */
    ALL(0, "All Data"),
    DEPT_AND_SUB(1, "Department and Sub-department Data"),
    DEPT(2, "Department Data"),
    SELF(3, "Self Data");

    /**
     * Numeric value representing the data scope level.
     */
    @Getter
    private Integer value;

    /**
     * Label describing the data scope level.
     */
    @Getter
    private String label;

    /**
     * Constructor to initialize DataScopeEnum with value and label.
     *
     * @param value Numeric value representing the data scope level
     * @param label Label describing the data scope level
     */
    DataScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
