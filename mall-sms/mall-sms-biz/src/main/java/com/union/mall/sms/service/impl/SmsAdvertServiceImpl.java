package com.union.mall.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.sms.converter.AdvertConverter;
import com.union.mall.sms.mapper.SmsAdvertMapper;
import com.union.mall.sms.model.entity.SmsAdvert;
import com.union.mall.sms.model.query.AdvertPageQuery;
import com.union.mall.sms.model.vo.AdvertPageVO;
import com.union.mall.sms.model.vo.BannerVO;
import com.union.mall.sms.service.SmsAdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SmsAdvertServiceImpl extends ServiceImpl<SmsAdvertMapper, SmsAdvert> implements SmsAdvertService {

    private final AdvertConverter advertConverter;

    /**
     * Advert pagination list
     *
     * @param queryParams query parameters
     * @return advert pagination list
     */
    @Override
    public Page<AdvertPageVO> getAdvertPage(AdvertPageQuery queryParams) {
        Page<SmsAdvert> page = this.baseMapper.getAdvertPage(new Page<>(queryParams.getPageNum(),
                        queryParams.getPageSize()),
                queryParams);
        return advertConverter.entity2PageVo(page);
    }

    /**
     * Get banner list
     */
    @Override
    public List<BannerVO> getBannerList() {

        List<SmsAdvert> entities = this.list(new LambdaQueryWrapper<SmsAdvert>().
                eq(SmsAdvert::getStatus, StatusEnum.ENABLE.getValue())
                .select(SmsAdvert::getTitle, SmsAdvert::getImageUrl, SmsAdvert::getRedirectUrl)
        );
        return advertConverter.entity2BannerVo(entities);
    }
}
