package com.union.mall.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.sms.model.entity.SmsCoupon;
import com.union.mall.sms.model.query.CouponPageQuery;
import com.union.mall.sms.model.vo.CouponPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SmsCouponMapper extends BaseMapper<SmsCoupon> {

    List<SmsCoupon> getCouponPage(Page<CouponPageVO> page, CouponPageQuery queryParams);
}

