package com.union.mall.sms.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Banner View Object")
@Data
public class BannerVO {

    @Schema(description = "Banner Title")
    private String title;

    @Schema(description = "Banner Image URL")
    private String imageUrl;

    @Schema(description = "Redirect URL")
    private String redirectUrl;

}
