package com.union.mall.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "User registration form")
@Data
public class UserRegisterForm {

    @Schema(description = "Login account")
    @NotBlank(message = "Login account cannot be empty")
    private String username;

    @Schema(description = "Mobile number")
    @Pattern(regexp = "(?:\\+84|0084|0)[235789][0-9]{1,2}[0-9]{7}(?:[^\\d]+|$)", message = "Invalid mobile number format")
    private String mobile;

    @Schema(description = "Password")
    @NotBlank(message = "Password cannot be empty")
    private String password;

//    @Schema(description = "Verification code")
//    @NotBlank(message = "Verification code cannot be empty")
//    private String code;

}
