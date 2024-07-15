package com.union.mall.pms.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Product pagination object")
@Data
public class SpuPageVO {

    @Schema(description="Product ID")
    private Long id;

    @Schema(description="Product name")
    private String name;

    @Schema(description="Product price (in cents)")
    private Long price;

    @Schema(description="Sales volume")
    private Integer sales;

    @Schema(description="Image URL")
    private String picUrl;

}
