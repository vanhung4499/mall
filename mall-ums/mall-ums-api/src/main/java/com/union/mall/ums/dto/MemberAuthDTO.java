package com.union.mall.ums.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthDTO {

    /**
     * Member ID
     */
    private Long id;

    /**
     * Member name (openId, mobile)
     */
    private String username;

    /**
     * Status (1: Active; 0: Disabled)
     */
    private Integer status;
}

