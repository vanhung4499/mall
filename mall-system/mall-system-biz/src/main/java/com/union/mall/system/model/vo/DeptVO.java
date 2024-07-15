package com.union.mall.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Department View Object")
@Data
public class DeptVO {

    @Schema(description = "Department ID")
    private Long id;

    @Schema(description = "Parent Department ID")
    private Long parentId;

    @Schema(description = "Department Name")
    private String name;

    @Schema(description = "Sort Order")
    private Integer sort;

    @Schema(description = "Status (1: Enabled; 0: Disabled)")
    private Integer status;

    @Schema(description = "Child Departments")
    private List<DeptVO> children;

    @Schema(description = "Creation Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @Schema(description = "Update Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

}
