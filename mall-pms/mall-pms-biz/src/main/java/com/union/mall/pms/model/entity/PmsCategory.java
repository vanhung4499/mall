package com.union.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class PmsCategory extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private String iconUrl;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer level;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer sort;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer visible;
}
