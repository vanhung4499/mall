package com.union.mall.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description ="User pagination object")
@Data
public class UserPageVO {

    @Schema(description="User ID")
    private Long id;

    @Schema(description="Username")
    private String username;

    @Schema(description="User nickname")
    private String nickname;

    @Schema(description="Mobile number")
    private String mobile;

    @Schema(description="Gender")
    private String genderLabel;

    @Schema(description="User avatar URL")
    private String avatar;

    @Schema(description="User email")
    private String email;

    @Schema(description="User status (1: enabled; 0: disabled)")
    private Integer status;

    @Schema(description="Department name")
    private String deptName;

    @Schema(description="Role names, separated by comma")
    private String roleNames;

    @Schema(description="Creation time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
