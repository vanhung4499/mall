package com.union.mall.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "User form object")
@Data
public class UserForm {

    @Schema(description = "User ID")
    private Long id;

    @Schema(description = "Username")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Nickname")
    @NotBlank(message = "Nickname cannot be empty")
    private String nickname;

    @Schema(description = "Mobile number")
    @Pattern(regexp = "(?:\\+84|0084|0)[235789][0-9]{1,2}[0-9]{7}(?:[^\\d]+|$)", message = "Invalid mobile number format")
    private String mobile;

    @Schema(description = "Gender")
    private Integer gender;

    @Schema(description = "User avatar")
    private String avatar;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "User status (1: Active; 0: Disabled)")
    private Integer status;

    @Schema(description = "Department ID")
    private Long deptId;

    @Schema(description = "Role IDs collection")
    @NotEmpty(message = "User roles cannot be empty")
    private List<Long> roleIds;

}
