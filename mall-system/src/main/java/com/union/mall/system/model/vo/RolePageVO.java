package com.union.mall.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Role Pagination Object")
@Data
public class RolePageVO {

    @Schema(description = "Role ID")
    private Long id;

    @Schema(description = "Role Name")
    private String name;

    @Schema(description = "Role Code")
    private String code;

    @Schema(description = "Role Status")
    private Integer status;

    @Schema(description = "Sorting")
    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
