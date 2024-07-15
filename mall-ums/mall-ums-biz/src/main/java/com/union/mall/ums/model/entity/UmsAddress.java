package com.union.mall.ums.model.entity;

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
public class UmsAddress extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Member ID
     */
    private Long memberId;

    /**
     * Consignee Name
     */
    private String consigneeName;

    /**
     * Consignee Mobile
     */
    private String consigneeMobile;

    /**
     * Province
     */
    private String province;

    /**
     * City
     */
    private String city;

    /**
     * Area
     */
    private String area;

    /**
     * Detailed Address
     */
    private String detailAddress;

    /**
     * Zip Code
     */
    private String zipCode;

    /**
     * Is Default Address (1: Yes; 0: No)
     */
    private Integer defaulted;

}
