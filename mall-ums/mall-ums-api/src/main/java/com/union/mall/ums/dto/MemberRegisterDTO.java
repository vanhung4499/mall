package com.union.mall.ums.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class MemberRegisterDTO {

    private Integer gender;

    private String nickName;

    private String mobile;

    private LocalDate birthday;

    private String avatarUrl;

    private String openid;

    private String sessionKey;

    private String city;

    private String country;

    private String language;

    private String province;

}
