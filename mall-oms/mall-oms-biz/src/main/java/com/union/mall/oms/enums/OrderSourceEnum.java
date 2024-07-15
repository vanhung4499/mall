package com.union.mall.oms.enums;

import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Getter
public enum OrderSourceEnum implements IBaseEnum<Integer> {

    APP(1, "APP"), // APP Order
    WEB(2, "WEB"), // Web
    ;

    OrderSourceEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }


    private Integer value;

    private String label;
}
