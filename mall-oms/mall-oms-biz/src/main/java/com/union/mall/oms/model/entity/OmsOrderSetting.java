package com.union.mall.oms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class OmsOrderSetting extends BaseEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Timeout for flash orders (minutes)
     */
    private Integer flashOrderOvertime;

    /**
     * Timeout for normal orders (minutes)
     */
    private Integer normalOrderOvertime;

    /**
     * Automatic confirmation time after delivery (days)
     */
    private Integer confirmOvertime;

    /**
     * Automatic completion of transaction time, cannot apply for returns (days)
     */
    private Integer finishOvertime;

    /**
     * Automatic evaluation time after order completion (days)
     */
    private Integer commentOvertime;

    /**
     * Member level [0 - Unlimited member level, applicable to all; Other values - Corresponding member level]
     */
    private Integer memberLevel;

    /**
     * Logical deletion [0->Normal; 1->Deleted]
     */
    private Integer deleted;

}
