package com.union.mall.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description ="Current logged-in user view object")
@Data
public class UserInfoVO {

    @Schema(description="User ID")
    private Long userId;

    @Schema(description="User nickname")
    private String nickname;

    @Schema(description="Avatar URL")
    private String avatar;

    @Schema(description="Set of user role codes")
    private Set<String> roles;

    @Schema(description="Set of user permission identifiers")
    private Set<String> perms;

}
