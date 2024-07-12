package com.union.mall.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description ="User profile view object")
@Data
public class UserProfileVO {

    @Schema(description="User ID")
    private Long id;

    @Schema(description="Login account")
    private String username;

    @Schema(description="User nickname")
    private String nickname;

    @Schema(description="Mobile number")
    private String mobile;

    @Schema(description="Avatar URL")
    private String avatar;

    @Schema(description="Set of user role names")
    private Set<String> roleNames;

    @Schema(description="Department name")
    private String deptName;

    @Schema(description="Email")
    private String email;

    @Schema(description="Gender")
    private String genderLabel;

}
