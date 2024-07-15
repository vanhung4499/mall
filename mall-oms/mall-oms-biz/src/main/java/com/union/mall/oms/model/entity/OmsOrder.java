package com.union.mall.oms.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OmsOrder extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Order Number
     */
    private String orderSn;

    /**
     * Total Order Amount (in cents)
     */
    private Long totalAmount;

    /**
     * Total Product Quantity
     */
    private Integer totalQuantity;

    /**
     * Order Source (0-PC order; 1-app order)
     */
    private Integer source;

    /**
     * Order Status (1-Pending Payment; 2-Pending Shipment; 3-Shipped; 4-Completed; 5-Closed; 6-Canceled;)
     */
    private Integer status;

    /**
     * Order Remark
     */
    private String remark;

    /**
     * Member ID
     */
    private Long memberId;

    /**
     * Used Coupon
     */
    private Long couponId;

    /**
     * Coupon Discount Amount (in cents)
     */
    private Long couponAmount;

    /**
     * Freight Amount (in cents)
     */
    private Long freightAmount;

    /**
     * Total Payable Amount (in cents)
     */
    private Long paymentAmount;

    /**
     * Payment Time
     */
    private Date paymentTime;

    /**
     * Payment Method [1->WeChat JSAPI; 2->Alipay; 3->Balance; 4->WeChat App;]
     */
    private Integer paymentMethod;

    /**
     * Merchant Order Number
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String outTradeNo;

    /**
     * WeChat Payment Order Number
     */
    private String transactionId;

    /**
     * Merchant Refund Order Number
     */
    private String outRefundNo;

    /**
     * WeChat Refund Order Number
     */
    private String refundId;

    /**
     * Delivery Time
     */
    private Date deliveryTime;

    /**
     * Receipt Confirmation Time
     */
    private Date receiveTime;

    /**
     * Comment Time
     */
    private Date commentTime;

    /**
     * Logical Deletion Flag (1: Deleted; 0: Normal)
     */
    private Integer deleted;

    @TableField(exist = false)
    private List<OmsOrderItem> orderItems;

}
