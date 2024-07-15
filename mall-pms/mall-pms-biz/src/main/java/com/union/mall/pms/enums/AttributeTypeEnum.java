package com.union.mall.pms.enums;

import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public enum AttributeTypeEnum {

    SPEC(1, "Specification"),
    ATTR(2, "Attribute");

    AttributeTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Getter
    private Integer value;

    @Getter
    private String name;

    public static AttributeTypeEnum getByValue(Integer value) {
        AttributeTypeEnum attributeTypeEnum = null;

        for (AttributeTypeEnum item : values()) {
            if (item.getValue().equals(value)) {
                attributeTypeEnum = item;
            }
        }
        return attributeTypeEnum;
    }

}
