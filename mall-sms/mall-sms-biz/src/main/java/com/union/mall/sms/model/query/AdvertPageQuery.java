package com.union.mall.sms.model.query;

import com.union.mall.common.core.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Advert Pagination Query Object")
@Data
public class AdvertPageQuery extends BasePageQuery {

    @Schema(description = "Keywords")
    private String keywords;

}
