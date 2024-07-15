package com.union.mall.pms.model.form;

import com.union.mall.pms.model.entity.PmsSku;
import lombok.Data;

import java.util.List;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class PmsSpuForm {

    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private Long originPrice;
    private Long price;
    private String picUrl;
    private String[] subPicUrls;
    private String description;
    private String detail;

    private List<PmsSpuAttributeForm> attrList;

    private List<PmsSpuAttributeForm> specList;

    private List<PmsSku> skuList;
}
