package com.union.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.sms.model.entity.SmsAdvert;
import com.union.mall.sms.model.query.AdvertPageQuery;
import com.union.mall.sms.model.vo.AdvertPageVO;
import com.union.mall.sms.model.vo.BannerVO;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface SmsAdvertService extends IService<SmsAdvert> {

    /**
     * Get advertisement page list
     *
     * @param queryParams query parameters
     * @return advertisement page
     */
    Page<AdvertPageVO> getAdvertPage(AdvertPageQuery queryParams);

    /**
     * Get banner list
     *
     * @return list of banners
     */
    List<BannerVO> getBannerList();
}
