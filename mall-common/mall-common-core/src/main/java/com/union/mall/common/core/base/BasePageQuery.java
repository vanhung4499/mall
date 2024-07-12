package com.union.mall.common.core.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Schema(description = "Base Page Query")
public class BasePageQuery implements Serializable {

    @Schema(description = "Page number", example = "1")
    private int pageNum = 1;

    @Schema(description = "Number of records per page", example = "10")
    private int pageSize = 10;
}
