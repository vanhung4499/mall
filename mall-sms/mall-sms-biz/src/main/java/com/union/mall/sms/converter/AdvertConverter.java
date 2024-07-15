package com.union.mall.sms.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.sms.model.entity.SmsAdvert;
import com.union.mall.sms.model.vo.AdvertPageVO;
import com.union.mall.sms.model.vo.BannerVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface AdvertConverter {

    AdvertPageVO entity2PageVo(SmsAdvert entity);

    Page<AdvertPageVO> entity2PageVo(Page<SmsAdvert> po);

    BannerVO entity2BannerVo(SmsAdvert entity);

    List<BannerVO> entity2BannerVo(List<SmsAdvert> entities);
}