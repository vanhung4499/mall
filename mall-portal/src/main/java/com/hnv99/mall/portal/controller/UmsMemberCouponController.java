package com.hnv99.mall.portal.controller;

import com.hnv99.mall.common.api.CommonResult;
import com.hnv99.mall.model.SmsCoupon;
import com.hnv99.mall.model.SmsCouponHistory;
import com.hnv99.mall.portal.domain.CartPromotionItem;
import com.hnv99.mall.portal.domain.SmsCouponHistoryDetail;
import com.hnv99.mall.portal.service.OmsCartItemService;
import com.hnv99.mall.portal.service.UmsMemberCouponService;
import com.hnv99.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "UmsMemberCouponController")
@Tag(name = "UmsMemberCouponController", description = "User Coupon Management")
@RequestMapping("/member/coupon")
public class UmsMemberCouponController {
    @Autowired
    private UmsMemberCouponService memberCouponService;
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("Receive specified coupon")
    @PostMapping(value = "/add/{couponId}")
    @ResponseBody
    public CommonResult add(@PathVariable Long couponId) {
        memberCouponService.add(couponId);
        return CommonResult.success(null,"Received successfully");
    }

    @ApiOperation("Get member's coupon history list")
    @ApiImplicitParam(name = "useStatus", value = "Coupon filter type: 0->Not used; 1->Used; 2->Expired",
            allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @GetMapping(value = "/listHistory")
    @ResponseBody
    public CommonResult<List<SmsCouponHistory>> listHistory(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<SmsCouponHistory> couponHistoryList = memberCouponService.listHistory(useStatus);
        return CommonResult.success(couponHistoryList);
    }

    @ApiOperation("Get member's coupon list")
    @ApiImplicitParam(name = "useStatus", value = "Coupon filter type: 0->Not used; 1->Used; 2->Expired",
            allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<List<SmsCoupon>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<SmsCoupon> couponList = memberCouponService.list(useStatus);
        return CommonResult.success(couponList);
    }

    @ApiOperation("Get the related coupons of the logged-in member's shopping cart")
    @ApiImplicitParam(name = "type", value = "Use available: 0->Not available; 1->Available",
            defaultValue = "1", allowableValues = "0,1", paramType = "path", dataType = "integer")
    @GetMapping(value = "/list/cart/{type}")
    @ResponseBody
    public CommonResult<List<SmsCouponHistoryDetail>> listCart(@PathVariable Integer type) {
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(memberService.getCurrentMember().getId(), null);
        List<SmsCouponHistoryDetail> couponHistoryList = memberCouponService.listCart(cartPromotionItemList, type);
        return CommonResult.success(couponHistoryList);
    }

    @ApiOperation("Get the related coupons of the current product")
    @GetMapping(value = "/listByProduct/{productId}")
    @ResponseBody
    public CommonResult<List<SmsCoupon>> listByProduct(@PathVariable Long productId) {
        List<SmsCoupon> couponHistoryList = memberCouponService.listByProduct(productId);
        return CommonResult.success(couponHistoryList);
    }
}

