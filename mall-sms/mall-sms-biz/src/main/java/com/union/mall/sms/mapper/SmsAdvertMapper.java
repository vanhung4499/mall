package com.union.mall.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.sms.model.entity.SmsAdvert;
import com.union.mall.sms.model.query.AdvertPageQuery;
import org.mapstruct.Mapper;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SmsAdvertMapper extends BaseMapper<SmsAdvert> {

    /**
     * Get paginated list of advertisements
     *
     * @param page          Pagination information
     * @param queryParams   Query parameters for filtering advertisements
     * @return              Page object containing the list of advertisements
     */
    Page<SmsAdvert> getAdvertPage(Page<SmsAdvert> page, AdvertPageQuery queryParams);
}
