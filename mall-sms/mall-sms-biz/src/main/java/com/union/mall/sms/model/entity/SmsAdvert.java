package com.union.mall.sms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class SmsAdvert extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date endTime;

    /**
     * Status
     */
    private Integer status;

    /**
     * Sort order
     */
    private Integer sort;

    /**
     * Redirect URL
     */
    private String redirectUrl;

    private String remark;

}
