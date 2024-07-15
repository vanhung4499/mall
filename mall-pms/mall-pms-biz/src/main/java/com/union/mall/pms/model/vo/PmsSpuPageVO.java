package com.union.mall.pms.model.vo;

import com.union.mall.pms.model.entity.PmsSku;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Accessors(chain = true)
public class PmsSpuPageVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    private Integer sales;

    private String picUrl;

    private String[] album;

    private String unit;

    private String description;

    private String detail;

    private Integer status;

    private String categoryName;

    private String brandName;

    private List<PmsSku> skuList;
}
