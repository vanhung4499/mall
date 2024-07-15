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
public class OmsOrderDelivery extends BaseEntity {

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
     * Delivery Company (Shipping Method)
     */
    private String deliveryCompany;

    /**
     * Delivery Tracking Number
     */
    private String deliverySn;

    /**
     * Receiver's Name
     */
    private String receiverName;

    /**
     * Receiver's Phone Number
     */
    private String receiverPhone;

    /**
     * Receiver's Postal Code
     */
    private String receiverPostCode;

    /**
     * Province/Municipality
     */
    private String receiverProvince;

    /**
     * City
     */
    private String receiverCity;

    /**
     * District
     */
    private String receiverRegion;

    /**
     * Detailed Address
     */
    private String receiverDetailAddress;

    /**
     * Remark
     */
    private String remark;

    /**
     * Delivery Status [0->In Transit; 1->Received]
     */
    private Integer deliveryStatus;

    /**
     * Shipment Time
     */
    private Date deliveryTime;

    /**
     * Receipt Confirmation Time
     */
    private Date receiveTime;

    /**
     * Logical Deletion [0->Normal; 1->Deleted]
     */
    private Integer deleted;

}
