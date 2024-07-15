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
@TableName(value = "sms_coupon_spu")
@Data
@Accessors(chain = true)
public class SmsCouponSpu implements Serializable {
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
     * Product ID
     */
    private Long spuId;

    /**
     * Product Name
     */
    private String spuName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
