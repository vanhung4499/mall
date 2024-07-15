package com.union.mall.sms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.base.IBaseEnum;
import com.union.mall.sms.converter.CouponConverter;
import com.union.mall.sms.enums.CouponApplicationScopeEnum;
import com.union.mall.sms.mapper.SmsCouponMapper;
import com.union.mall.sms.model.entity.SmsCoupon;
import com.union.mall.sms.model.entity.SmsCouponSpu;
import com.union.mall.sms.model.entity.SmsCouponSpuCategory;
import com.union.mall.sms.model.form.CouponForm;
import com.union.mall.sms.model.query.CouponPageQuery;
import com.union.mall.sms.model.vo.CouponPageVO;
import com.union.mall.sms.service.SmsCouponService;
import com.union.mall.sms.service.SmsCouponSpuCategoryService;
import com.union.mall.sms.service.SmsCouponSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements SmsCouponService {

    private final CouponConverter couponConverter;

    private final SmsCouponSpuCategoryService smsCouponSpuCategoryService;

    private final SmsCouponSpuService smsCouponSpuService;

    /**
     * Coupon pagination list
     *
     * @param queryParams query parameters
     * @return coupon pagination list
     */
    @Override
    public Page<CouponPageVO> getCouponPage(CouponPageQuery queryParams) {
        Page<CouponPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // Query data
        List<SmsCoupon> couponList = this.baseMapper.getCouponPage(page, queryParams);
        // Entity conversion
        List<CouponPageVO> records = couponConverter.entity2PageVO(couponList);
        page.setRecords(records);
        return page;
    }

    /**
     * Get coupon form data
     *
     * @param couponId coupon ID
     * @return coupon form data
     */
    @Override
    public CouponForm getCouponFormData(Long couponId) {
        SmsCoupon entity = this.getById(couponId);
        // Entity conversion: entity -> form
        CouponForm couponForm = couponConverter.entity2Form(entity);

        Integer applicationScope = couponForm.getApplicationScope();

        CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);
        if (applicationScopeEnum != null) {
            switch (applicationScopeEnum) {
                case SPU_CATEGORY:
                    List<SmsCouponSpuCategory> couponSpuCategoryList = smsCouponSpuCategoryService.list(new LambdaQueryWrapper<SmsCouponSpuCategory>()
                            .eq(SmsCouponSpuCategory::getCouponId, couponId)
                            .select(SmsCouponSpuCategory::getCategoryId)
                    );
                    List<Long> categoryIds = couponSpuCategoryList.stream().map(item -> item.getCategoryId()).collect(Collectors.toList());
                    couponForm.setSpuCategoryIds(categoryIds);
                    break;
                case SPU:
                    List<SmsCouponSpu> couponSpuList = smsCouponSpuService.list(new LambdaQueryWrapper<SmsCouponSpu>()
                            .eq(SmsCouponSpu::getCouponId, couponId)
                            .select(SmsCouponSpu::getSpuId)
                    );
                    List<Long> spuIds = couponSpuList.stream().map(item -> item.getSpuId()).collect(Collectors.toList());
                    couponForm.setSpuIds(spuIds);
                    break;
            }
        }
        return couponForm;
    }

    /**
     * Save coupon
     *
     * @param couponForm coupon form
     * @return true if saved successfully, false otherwise
     */
    @Override
    public boolean saveCoupon(CouponForm couponForm) {
        SmsCoupon entity = couponConverter.form2Entity(couponForm);
        boolean result = this.save(entity);

        if (result) {
            // Save corresponding relationships based on coupon application scope
            // SPU category: Coupon <-> Category
            // SPU: Coupon <-> Product
            Long couponId = entity.getId();
            Integer applicationScope = couponForm.getApplicationScope();
            CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);

            Assert.isTrue(applicationScopeEnum != null, "Please specify the coupon application scope");
            switch (applicationScopeEnum) {
                case SPU_CATEGORY:
                    List<Long> spuCategoryIds = couponForm.getSpuCategoryIds();
                    if (CollectionUtil.isNotEmpty(spuCategoryIds)) {
                        List<SmsCouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream()
                                .map(spuCategoryId -> new SmsCouponSpuCategory().setCouponId(couponId).setCategoryId(spuCategoryId))
                                .collect(Collectors.toList());
                        smsCouponSpuCategoryService.saveBatch(smsCouponSpuCategories);
                    }
                    break;

                case SPU:
                    List<Long> spuIds = couponForm.getSpuIds();
                    if (CollectionUtil.isNotEmpty(spuIds)) {
                        List<SmsCouponSpu> smsCouponSpuList = spuIds.stream()
                                .map(spuId -> new SmsCouponSpu().setCouponId(couponId).setSpuId(spuId))
                                .collect(Collectors.toList());
                        smsCouponSpuService.saveBatch(smsCouponSpuList);
                    }
                    break;
            }
        }
        return result;
    }

    /**
     * Update coupon
     *
     * @param couponId   coupon ID
     * @param couponForm coupon form
     * @return true if updated successfully, false otherwise
     */
    @Override
    public boolean updateCoupon(Long couponId, CouponForm couponForm) {
        SmsCoupon entity = couponConverter.form2Entity(couponForm);
        boolean result = this.updateById(entity);

        if (result) {
            // Save corresponding relationships based on coupon application scope
            // All: Delete all relationships
            // SPU category: Coupon <-> Category
            // SPU: Coupon <-> Product
            Integer applicationScope = couponForm.getApplicationScope();
            CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);

            Assert.isTrue(applicationScopeEnum != null, "Please specify the coupon application scope");
            switch (applicationScopeEnum) {
                case ALL:
                    smsCouponSpuCategoryService.remove(new LambdaQueryWrapper<SmsCouponSpuCategory>()
                            .eq(SmsCouponSpuCategory::getCouponId, couponId)
                    );
                    smsCouponSpuService.remove(new LambdaQueryWrapper<SmsCouponSpu>()
                            .eq(SmsCouponSpu::getCouponId, couponId)
                    );

                    break;
                case SPU_CATEGORY:
                    List<Long> spuCategoryIds = couponForm.getSpuCategoryIds();
                    if (CollectionUtil.isNotEmpty(spuCategoryIds)) {
                        smsCouponSpuCategoryService.remove(new LambdaQueryWrapper<SmsCouponSpuCategory>()
                                .eq(SmsCouponSpuCategory::getCouponId, couponId)
                        );
                        List<SmsCouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream()
                                .map(spuCategoryId -> new SmsCouponSpuCategory().setCouponId(couponId)
                                        .setCategoryId(spuCategoryId))
                                .collect(Collectors.toList());
                        smsCouponSpuCategoryService.saveBatch(smsCouponSpuCategories);
                    }
                    break;
                case SPU:
                    List<Long> spuIds = couponForm.getSpuIds();
                    if (CollectionUtil.isNotEmpty(spuIds)) {
                        smsCouponSpuService.remove(new LambdaQueryWrapper<SmsCouponSpu>()
                                .eq(SmsCouponSpu::getCouponId, couponId)
                        );
                        List<SmsCouponSpu> smsCouponSpuList = spuIds.stream()
                                .map(spuId -> new SmsCouponSpu().setCouponId(couponId).setSpuId(spuId))
                                .collect(Collectors.toList());
                        smsCouponSpuService.saveBatch(smsCouponSpuList);
                    }
                    break;
            }
        }

        return result;
    }

    /**
     * Delete coupons
     *
     * @param idsStr coupon IDs separated by commas
     * @return true if deleted successfully, false otherwise
     */
    @Override
    public boolean deleteCoupons(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "Deleted coupon data is empty");
        // Logical deletion
        List<Long> ids = Arrays.asList(idsStr.split(",")).stream()
                .map(idStr -> Long.parseLong(idStr))
                .collect(Collectors.toList());
        boolean result = this.removeByIds(ids);
        return result;
    }
}
