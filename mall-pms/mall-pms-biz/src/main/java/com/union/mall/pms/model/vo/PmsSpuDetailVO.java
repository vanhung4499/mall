package com.union.mall.pms.model.vo;

import com.union.mall.pms.model.entity.PmsSku;
import com.union.mall.pms.model.entity.PmsSpuAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Schema(description = "Product detail view object")
public class PmsSpuDetailVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    @Schema(description="Main product image")
    private String picUrl;

    @Schema(description="Secondary product images")
    private String[] subPicUrls;

    private String description;

    private String detail;

    private List<PmsSpuAttribute> attrList;

    private List<PmsSpuAttribute> specList;

    private List<PmsSku> skuList;

}
