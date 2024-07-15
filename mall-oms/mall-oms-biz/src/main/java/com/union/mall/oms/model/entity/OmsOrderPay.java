package com.union.mall.oms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Builder
public class OmsOrderPay extends BaseEntity {

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
     * Payment serial number
     */
    private String paySn;

    /**
     * Total payable amount (in cents)
     */
    private Long payAmount;

    /**
     * Payment time
     */
    private Date payTime;

    /**
     * Payment method [1->Alipay; 2->WeChat; 3->UnionPay; 4->Cash on delivery;]
     */
    private Integer payType;

    /**
     * Payment status
     */
    private Integer payStatus;

    /**
     * Confirmation time
     */
    private Date confirmTime;

    /**
     * Callback content
     */
    private String callbackContent;

    /**
     * Callback time
     */
    private Date callbackTime;

    /**
     * Transaction description
     */
    private String paySubject;

    /**
     * Logical deletion [0->Normal; 1->Deleted]
     */
    private Integer deleted;

}
