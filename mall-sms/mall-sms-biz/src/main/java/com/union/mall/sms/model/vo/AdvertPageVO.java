package com.union.mall.sms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Advert Pagination View Object")
@Data
public class AdvertPageVO {

    @Schema(description = "Advert ID")
    private Integer id;

    @Schema(description = "Advert Title")
    private String title;

    @Schema(description = "Advert Image URL")
    private String picUrl;

    @Schema(description = "Start Time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @Schema(description = "End Time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @Schema(description = "Status")
    private Integer status;

    private Integer sort;

    private String redirectUrl;

    private String remark;

}
