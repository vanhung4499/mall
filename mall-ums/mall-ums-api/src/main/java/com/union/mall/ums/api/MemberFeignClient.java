package com.union.mall.ums.api;

import com.union.mall.common.core.result.Result;
import com.union.mall.pms.model.vo.ProductHistoryVO;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.dto.MemberAuthDTO;
import com.union.mall.ums.dto.MemberRegisterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@FeignClient(name = "mall-ums", contextId = "member")
public interface MemberFeignClient {

    /**
     * Add a new member
     *
     * @param member
     * @return
     */
    @PostMapping("/app-api/v1/members")
    Result<Long> registerMember(@RequestBody MemberRegisterDTO member);

    /**
     * Get the openid of the member
     *
     * @return
     */
    @PostMapping("/app-api/v1/members/{memberId}/openid")
    Result<String> getMemberOpenId(@PathVariable Long memberId);

    /**
     * Deduct member balance
     */
    @PutMapping("/app-api/v1/members/{memberId}/balances/_deduct")
    <T> Result<T> deductBalance(@PathVariable Long memberId, @RequestParam Long amount);

    /**
     * Add product view history
     */
    @PostMapping("/app-api/v1/members/view/history")
    <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product);

    /**
     * Get member authentication information by openid
     *
     * @param openid
     * @return
     */
    @GetMapping("/app-api/v1/members/openid/{openid}")
    Result<MemberAuthDTO> loadUserByOpenId(@PathVariable String openid);

    /**
     * Get member authentication information by mobile number
     *
     * @param mobile mobile number
     * @return member authentication information
     */
    @GetMapping("/app-api/v1/members/mobile/{mobile}")
    Result<MemberAuthDTO> loadUserByMobile(@PathVariable String mobile);

    /**
     * Get the list of member addresses
     *
     * @param memberId
     * @return
     */
    @GetMapping("/app-api/v1/members/{memberId}/addresses")
    Result<List<MemberAddressDTO>> listMemberAddresses(@PathVariable Long memberId);

}
