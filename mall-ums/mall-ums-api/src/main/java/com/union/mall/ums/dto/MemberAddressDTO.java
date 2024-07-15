package com.union.mall.ums.dto;

import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class MemberAddressDTO {

    private Long id;

    private Long memberId;

    private String consigneeName;

    private String consigneeMobile;

    private String province;

    private String city;

    private String area;

    private String detailAddress;

    private Integer defaulted;

}