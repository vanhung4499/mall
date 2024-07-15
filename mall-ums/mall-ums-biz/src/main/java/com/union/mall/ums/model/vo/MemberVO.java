package com.union.mall.ums.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Member View Object")
@Data
public class MemberVO {

    @Schema(description = "Member ID")
    private Long id;

    @Schema(description = "Member Nickname")
    private String nickName;

    @Schema(description = "Member Avatar URL")
    private String avatarUrl;

    @Schema(description = "Member Mobile")
    private String mobile;

    @Schema(description = "Member Balance (unit: cents)")
    private Long balance;

}
