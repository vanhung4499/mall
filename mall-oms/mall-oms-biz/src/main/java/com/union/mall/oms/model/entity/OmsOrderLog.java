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
public class OmsOrderLog extends BaseEntity {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Order ID
     */
    private Long orderId;

    /**
     * Operator [User; System; Backstage Administrator]
     */
    private String user;

    /**
     * Operation details
     */
    private String detail;

    /**
     * Order status at the time of operation
     */
    private Integer orderStatus;

    /**
     * Remark
     */
    private String remark;

    /**
     * Logical deletion [0->Normal; 1->Deleted]
     */
    private Integer deleted;

}
