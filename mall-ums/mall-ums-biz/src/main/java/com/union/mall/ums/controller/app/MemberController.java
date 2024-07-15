package com.union.mall.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.union.mall.pms.model.vo.ProductHistoryVO;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.dto.MemberAuthDTO;
import com.union.mall.ums.dto.MemberRegisterDTO;
import com.union.mall.ums.service.UmsMemberService;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.ums.model.entity.UmsMember;
import com.union.mall.ums.model.vo.MemberVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "App-Member Management")
@RestController
@RequestMapping("/app-api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final UmsMemberService memberService;

    @Operation(summary= "Get openid by member ID")
    @GetMapping("/{memberId}/openid")
    public Result<String> getMemberById(@Parameter(name = "Member ID") @PathVariable Long memberId) {
        UmsMember member = memberService.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getId, memberId)
                .select(UmsMember::getOpenid));
        String openid = member.getOpenid();
        return Result.success(openid);
    }

    @Operation(summary= "Add member")
    @PostMapping
    public Result<Long> addMember(@RequestBody MemberRegisterDTO member) {
        Long memberId = memberService.addMember(member);
        return Result.success(memberId);
    }

    @Operation(summary= "Get current logged-in member information")
    @GetMapping("/me")
    public Result<MemberVO> getCurrMemberInfo() {
        MemberVO memberVO = memberService.getCurrMemberInfo();
        return Result.success(memberVO);
    }

    @Operation(summary= "Deduct member balance")
    @PutMapping("/{memberId}/balances/_deduct")
    public Result deductBalance(@PathVariable Long memberId, @RequestParam Long amount) {
        boolean result = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .setSql("balance = balance - " + amount)
                .eq(UmsMember::getId, memberId));
        return Result.judge(result);
    }

    @Operation(summary= "Add browsing history")
    @PostMapping("/view/history")
    public <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product) {
        Long memberId = SecurityUtils.getMemberId();
        memberService.addProductViewHistory(product, memberId);
        return Result.success();
    }

    @Operation(summary= "Get browsing history")
    @GetMapping("/view/history")
    public Result<Set<ProductHistoryVO>> getProductViewHistory() {
        Long memberId = SecurityUtils.getMemberId();
        Set<ProductHistoryVO> historyList = memberService.getProductViewHistory(memberId);
        return Result.success(historyList);
    }

    @Operation(summary= "Get member authentication information by openid")
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthDTO> getMemberByOpenid(@Parameter(name = "WeChat unique identity") @PathVariable String openid) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByOpenid(openid);
        return Result.success(memberAuthInfo);
    }

    @Operation(summary= "Get member authentication information by mobile number", hidden = true)
    @GetMapping("/mobile/{mobile}")
    public Result<MemberAuthDTO> getMemberByMobile(
            @Parameter(name = "Mobile number") @PathVariable String mobile
    ) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByMobile(mobile);
        return Result.success(memberAuthInfo);
    }

    @Operation(summary ="Get member address list")
    @GetMapping("/{memberId}/addresses")
    public Result<List<MemberAddressDTO>> listMemberAddress(
            @Parameter(name = "Member ID") @PathVariable Long memberId
    ) {
        List<MemberAddressDTO> addresses = memberService.listMemberAddress(memberId);
        return Result.success(addresses);
    }
}
