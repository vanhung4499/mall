package com.union.mall.sms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@TableName(value = "sms_coupon_spu_category")
@Data
@Accessors(chain = true)
public class SmsCouponSpuCategory implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Coupon ID
     */
    private Long couponId;

    /**
     * Product Category ID
     */
    private Long categoryId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
