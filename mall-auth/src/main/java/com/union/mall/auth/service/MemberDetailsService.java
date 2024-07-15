package com.union.mall.auth.service;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */

import cn.hutool.core.lang.Assert;
import com.union.mall.auth.model.MemberDetails;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.core.result.ResultCode;
import com.union.mall.ums.api.MemberFeignClient;
import com.union.mall.ums.dto.MemberAuthDTO;
import com.union.mall.ums.dto.MemberRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Member authentication service for mall members.
 *
 * @author haoxr
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class MemberDetailsService {

    private final MemberFeignClient memberFeignClient;

    /**
     * Authentication method using mobile number.
     *
     * @param mobile Mobile number
     * @return User details
     */
    public UserDetails loadUserByMobile(String mobile) {
        Result<MemberAuthDTO> result = memberFeignClient.loadUserByMobile(mobile);

        MemberAuthDTO memberAuthInfo;
        if (!(Result.isSuccess(result) && (memberAuthInfo = result.getData()) != null)) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        MemberDetails userDetails = new MemberDetails(memberAuthInfo);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("This account has been disabled!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("This account has been locked!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("This account has expired!");
        }
        return userDetails;
    }

    /**
     * Retrieve user information by openid.
     *
     * @param openid Unique identity for WeChat public platform
     * @return {@link MemberDetails}
     */
    public UserDetails loadUserByOpenid(String openid) {
        // Retrieve WeChat user authentication information by openid
        Result<MemberAuthDTO> getMemberAuthInfoResult = memberFeignClient.loadUserByOpenId(openid);

        MemberAuthDTO memberAuthInfo = null;

        // If member does not exist, register as a new member
        if (ResultCode.USER_NOT_EXIST.getCode().equals(getMemberAuthInfoResult.getCode())) {
            MemberRegisterDTO memberRegisterInfo = new MemberRegisterDTO();
            memberRegisterInfo.setOpenid(openid);
            memberRegisterInfo.setNickName("WeChat User");
            // Register member
            Result<Long> registerMemberResult = memberFeignClient.registerMember(memberRegisterInfo);
            // If registration is successful, assign member information to authentication information
            Long memberId;
            if (Result.isSuccess(registerMemberResult) && (memberId = registerMemberResult.getData()) != null) {
                memberAuthInfo = new MemberAuthDTO(memberId, openid, StatusEnum.ENABLE.getValue());
            }
        } else {
            Assert.isTrue((memberAuthInfo = getMemberAuthInfoResult.getData()) != null, "Failed to fetch member authentication information");
        }

        // If user does not exist
        if (memberAuthInfo == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }

        UserDetails userDetails = new MemberDetails(memberAuthInfo);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("This account has been disabled!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("This account has been locked!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("This account has expired!");
        }
        return userDetails;
    }

}
