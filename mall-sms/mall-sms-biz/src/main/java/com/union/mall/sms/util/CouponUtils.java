package com.union.mall.sms.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.union.mall.common.core.base.IBaseEnum;
import com.union.mall.sms.enums.CouponFaceValueTypeEnum;
import com.union.mall.sms.enums.ValidityPeriodTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public class CouponUtils {

    /**
     * Calculate coupon face value label
     *
     * @param faceValueType face value type
     * @param faceValue     face value
     * @param discount      discount rate
     * @return face value label
     */
    public static String getFaceValue(Integer faceValueType, Long faceValue, BigDecimal discount) {
        String faceValueLabel = null;

        if (faceValueType == null) {
            return null;
        }

        CouponFaceValueTypeEnum couponFaceValueTypeEnum = IBaseEnum.getEnumByValue(faceValueType, CouponFaceValueTypeEnum.class);
        switch (couponFaceValueTypeEnum) {
            case CASH:
                faceValueLabel = NumberUtil.toStr(NumberUtil.div(faceValue, new Float(100), 2)) + "$";
                break;
            case DISCOUNT:
                faceValueLabel = NumberUtil.mul(discount, 10) + "折";
                break;
        }
        return faceValueLabel;
    }

    /**
     * Calculate coupon validity period label
     *
     * @param validityPeriodType validity period type
     * @param validityDays       days of validity
     * @param validityBeginTime  start time of validity
     * @param validityEndTime    end time of validity
     * @return validity period label
     */
    public static String getValidityPeriod(Integer validityPeriodType, Integer validityDays, Date validityBeginTime, Date validityEndTime) {
        String validityPeriodLabel = null;
        if (validityPeriodType == null) {
            return null;
        }
        ValidityPeriodTypeEnum validityPeriodTypeEnum = IBaseEnum.getEnumByValue(validityPeriodType, ValidityPeriodTypeEnum.class);
        switch (validityPeriodTypeEnum) {
            case DATE_RANGE:
                validityPeriodLabel = DateUtil.format(validityBeginTime, "yyyy/MM/dd") + "~" + DateUtil.format(validityEndTime, "yyyy/MM/dd");
                break;
            case FIXED_DAYS:
                validityPeriodLabel = "Valid for " + validityDays + " days after receiving";
                break;
        }
        return validityPeriodLabel;
    }
}
