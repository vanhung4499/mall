package com.union.mall.sms.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.sms.model.form.CouponForm;
import com.union.mall.sms.model.query.CouponPageQuery;
import com.union.mall.sms.model.vo.CouponPageVO;
import com.union.mall.sms.service.SmsCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Coupon Management")
@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class SmsCouponController {

    private final SmsCouponService couponService;

    @Operation(summary= "Coupon Pagination List")
    @GetMapping("/pages")
    public PageResult getCouponPage(CouponPageQuery queryParams) {
        Page<CouponPageVO> result = couponService.getCouponPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary= "Coupon Form Data")
    @GetMapping("/{couponId}/form_data")
    public Result<CouponForm> getCouponFormData(@Parameter(name = "Coupon ID") @PathVariable Long couponId) {
        CouponForm couponForm = couponService.getCouponFormData(couponId);
        return Result.success(couponForm);
    }

    @Operation(summary ="Add Coupon")
    @PostMapping
    public Result saveCoupon(@RequestBody @Valid CouponForm couponForm) {
        boolean result = couponService.saveCoupon(couponForm);
        return Result.judge(result);
    }

    @Operation(summary ="Update Coupon")
    @PutMapping("/{couponId}")
    public Result updateCoupon(
            @PathVariable Long couponId,
            @RequestBody @Valid CouponForm couponForm
    ) {
        boolean result = couponService.updateCoupon(couponId,couponForm);
        return Result.judge(result);
    }

    @Operation(summary= "Delete Coupons")
    @DeleteMapping("/{ids}")
    public Result deleteCoupons(@Parameter(name = "Coupon IDs separated by commas") @PathVariable String ids) {
        boolean result = couponService.deleteCoupons(ids);
        return Result.judge(result);
    }
}
