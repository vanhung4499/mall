package com.union.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.common.core.base.BaseEntity;
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
public class PmsSpu extends BaseEntity {

    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String brandName;

    @TableField(exist = false)
    private List<PmsSku> skuList;
}
