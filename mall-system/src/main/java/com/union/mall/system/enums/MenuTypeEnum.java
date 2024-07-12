package com.union.mall.system.enums;

import com.union.mall.common.core.base.IBaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Menu type enumeration
 *
 * Represents different types of menus with associated values and labels.
 * Implemented as an enum for type safety and clarity.
 *
 * @author vanhung4499
 */
public enum MenuTypeEnum implements IBaseEnum<Integer> {

    NULL(0, null),       // Represents a null menu type with value 0 and no specific label
    MENU(1, "Menu"),     // Represents a menu type with value 1 and label "Menu"
    CATALOG(2, "Catalog"), // Represents a catalog type with value 2 and label "Catalog"
    EXTLINK(3, "External Link"), // Represents an external link type with value 3 and label "External Link"
    BUTTON(4, "Button");  // Represents a button type with value 4 and label "Button"

    @Getter
    @EnumValue // Annotation provided by Mybatis-Plus to indicate this field is used for database insertion
    private Integer value;

    @Getter
    // @JsonValue // Indicates that this field should be used for serialization of the enum
    private String label;

    // Constructor for MenuTypeEnum to initialize value and label
    MenuTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

}
