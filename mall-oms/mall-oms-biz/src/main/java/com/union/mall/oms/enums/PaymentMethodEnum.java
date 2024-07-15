package com.union.mall.oms.enums;

import com.union.mall.common.core.base.IBaseEnum;
import lombok.Getter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Getter
public enum PaymentMethodEnum implements IBaseEnum<Integer> {

    BANK(1, "Bank Transfer Payment"),
    MOMO(2, "MoMo Payment"),
    BALANCE(3, "Member Balance Payment"),
    ZALOPAY(4, "Zalo Pay"),
    COD(5, "Cash on Delivery");

    PaymentMethodEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    private Integer value;

    private String label;
}
