package com.union.mall.common.core.base;

import cn.hutool.core.util.ObjectUtil;

import java.util.EnumSet;

/**
 * Common interface for enums
 *
 * @author vanhung4499
 */
public interface IBaseEnum<T> {

    T getValue();

    String getLabel();

    /**
     * Get enum by value
     *
     * @param value
     * @param clazz
     * @param <E>   Enum type
     * @return Enum
     */
    static <E extends Enum<E> & IBaseEnum> E getEnumByValue(Object value, Class<E> clazz) {

        if (value == null) {
            return null;
        }

        EnumSet<E> allEnums = EnumSet.allOf(clazz); // Get all enums of the type
        E matchEnum = allEnums.stream()
                .filter(e -> ObjectUtil.equal(e.getValue(), value))
                .findFirst()
                .orElse(null);
        return matchEnum;
    }

    /**
     * Get label by value
     *
     * @param value
     * @param clazz
     * @param <E>
     * @return
     */
    static <E extends Enum<E> & IBaseEnum> String getLabelByValue(Object value, Class<E> clazz) {
        if (value == null) {
            return null;
        }

        EnumSet<E> allEnums = EnumSet.allOf(clazz); // Get all enums of the type
        E matchEnum = allEnums.stream()
                .filter(e -> ObjectUtil.equal(e.getValue(), value))
                .findFirst()
                .orElse(null);

        String label = null;
        if (matchEnum != null) {
            label = matchEnum.getLabel();
        }
        return label;
    }

    /**
     * Get value by label
     *
     * @param label
     * @param clazz
     * @param <E>
     * @return
     */
    static <E extends Enum<E> & IBaseEnum> Object getValueByLabel(String label, Class<E> clazz) {
        if (label == null) {
            return null;
        }

        EnumSet<E> allEnums = EnumSet.allOf(clazz); // Get all enums of the type
        String finalLabel = label;
        E matchEnum = allEnums.stream()
                .filter(e -> ObjectUtil.equal(e.getLabel(), finalLabel))
                .findFirst()
                .orElse(null);

        Object value = null;
        if (matchEnum != null) {
            value = matchEnum.getValue();
        }
        return value;
    }

}
