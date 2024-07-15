package com.union.mall.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "File Object")
@Data
public class FileInfoVO {

    @Schema(description = "File Name")
    private String name;

    @Schema(description = "File URL")
    private String url;

}
