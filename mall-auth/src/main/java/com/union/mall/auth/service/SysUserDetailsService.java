package com.union.mall.auth.service;

import cn.hutool.core.lang.Assert;
import com.union.mall.auth.model.LoginUserInfo;
import com.union.mall.auth.model.SysUserDetails;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.system.api.UserFeignClient;
import com.union.mall.system.dto.UserAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Implementation class for loading system user details.
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    /**
     * Retrieve user information (username, password, and role permissions) by username.
     * <p>
     * Username and password are used for subsequent authentication. Upon successful authentication, permissions are granted to the user.
     *
     * @param username Username
     * @return {@link SysUserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAuthInfo userAuthInfo = userFeignClient.getUserAuthInfo(username);

        Assert.isTrue(userAuthInfo != null, "User does not exist");

        if (!StatusEnum.ENABLE.getValue().equals(userAuthInfo.getStatus())) {
            throw new DisabledException("This account has been disabled!");
        }

        return new SysUserDetails(userAuthInfo);
    }

    /**
     * Retrieve login user information.
     *
     * @return LoginUserInfo
     */
    public LoginUserInfo getLoginUserInfo() {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setId(123L);
        return loginUserInfo;
    }
}
