package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.GlobalConstants;
import com.union.mall.common.core.constant.RedisConstants;
import com.union.mall.common.core.constant.SystemConstants;
import com.union.mall.common.security.service.PermissionService;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.system.converter.UserConverter;
import com.union.mall.system.model.dto.UserAuthInfo;
import com.union.mall.system.mapper.SysUserMapper;
import com.union.mall.system.model.bo.UserBO;
import com.union.mall.system.model.bo.UserFormBO;
import com.union.mall.system.model.bo.UserProfileBO;
import com.union.mall.system.model.entity.SysUser;
import com.union.mall.system.model.form.UserForm;
import com.union.mall.system.model.form.UserRegisterForm;
import com.union.mall.system.model.query.UserPageQuery;
import com.union.mall.system.model.vo.UserExportVO;
import com.union.mall.system.model.vo.UserInfoVO;
import com.union.mall.system.model.vo.UserPageVO;
import com.union.mall.system.model.vo.UserProfileVO;
import com.union.mall.system.service.SysRoleService;
import com.union.mall.system.service.SysUserRoleService;
import com.union.mall.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Object storage service implementation
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    private final SysUserRoleService userRoleService;

    private final SysRoleService roleService;

    private final UserConverter userConverter;

    private final PermissionService permissionService;

    private final StringRedisTemplate redisTemplate;

    /**
     * Get paginated list of users.
     *
     * @param queryParams Query parameters
     * @return {@link UserPageVO}
     */
    @Override
    public IPage<UserPageVO> getUserPage(UserPageQuery queryParams) {

        // Build parameters
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<UserBO> page = new Page<>(pageNum, pageSize);

        // Query data
        Page<UserBO> userBoPage = this.baseMapper.getUserPage(page, queryParams);

        // Entity conversion
        return userConverter.bo2Vo(userBoPage);
    }

    /**
     * Get user details.
     *
     * @param userId User ID
     * @return {@link UserForm}
     */
    @Override
    public UserForm getUserFormData(Long userId) {
        UserFormBO userFormBO = this.baseMapper.getUserDetail(userId);
        // Entity conversion from BO to form
        return userConverter.bo2Form(userFormBO);
    }

    /**
     * Create a new user.
     *
     * @param userForm User form object
     * @return true if successful, false otherwise
     */
    @Override
    public boolean saveUser(UserForm userForm) {

        String username = userForm.getUsername();

        // Check if username already exists
        long count = this.count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        Assert.isTrue(count == 0, "Username already exists");

        // Convert form to entity
        SysUser entity = userConverter.form2Entity(userForm);

        // Set default encrypted password
        String defaultEncryptPwd = passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD);
        entity.setPassword(defaultEncryptPwd);

        // Save user
        boolean result = this.save(entity);

        if (result) {
            // Save user roles
            userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return result;
    }


    /**
     * Update user information.
     *
     * @param userId   User ID
     * @param userForm User form object
     * @return true if successfully updated, false otherwise
     */
    @Override
    @Transactional
    public boolean updateUser(Long userId, UserForm userForm) {

        String username = userForm.getUsername();

        // Check if username already exists except for the current user
        long count = this.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .ne(SysUser::getId, userId)
        );
        Assert.isTrue(count == 0, "Username already exists");

        // Convert form to entity
        SysUser entity = userConverter.form2Entity(userForm);

        // Update user
        boolean result = this.updateById(entity);

        if (result) {
            // Save user roles
            userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return result;
    }

    /**
     * Delete users by their IDs.
     *
     * @param idsStr Comma-separated list of user IDs
     * @return true if successfully deleted, false otherwise
     */
    @Override
    public boolean deleteUsers(String idsStr) {
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return this.removeByIds(ids);
    }

    /**
     * Update user password.
     *
     * @param userId   User ID
     * @param password New password
     * @return true if successfully updated, false otherwise
     */
    @Override
    public boolean updatePassword(Long userId, String password) {
        return this.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getPassword, passwordEncoder.encode(password))
        );
    }

    /**
     * Get authentication information by username.
     *
     * @param username Username
     * @return User authentication information {@link UserAuthInfo}
     */
    @Override
    public UserAuthInfo getUserAuthInfo(String username) {
        UserAuthInfo userAuthInfo = this.baseMapper.getUserAuthInfo(username);
        if (userAuthInfo != null) {
            Set<String> roles = userAuthInfo.getRoles();
            if (CollectionUtil.isNotEmpty(roles)) {
                // Get the maximum data scope (Currently, smaller DataScope means larger data access range, so we retrieve the smallest DataScope in the role list)
                Integer dataScope = roleService.getMaxDataRangeDataScope(roles);
                userAuthInfo.setDataScope(dataScope);
            }
        }
        return userAuthInfo;
    }

    /**
     * Get the list of users for export.
     *
     * @param queryParams Query parameters
     * @return List of {@link UserExportVO}
     */
    @Override
    public List<UserExportVO> listExportUsers(UserPageQuery queryParams) {
        return this.baseMapper.listExportUsers(queryParams);
    }

    /**
     * Get information of the current logged-in user.
     *
     * @return {@link UserInfoVO} User information
     */
    @Override
    public UserInfoVO getCurrentUserInfo() {
        // Fetch logged-in user entity
        SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, SecurityUtils.getUsername())
                .select(
                        SysUser::getId,
                        SysUser::getNickname,
                        SysUser::getAvatar
                )
        );

        // Convert entity to VO
        UserInfoVO userInfoVO = userConverter.entity2UserInfoVo(user);

        // Get user roles
        Set<String> roles = SecurityUtils.getRoles();
        userInfoVO.setRoles(roles);

        // Get user permissions
        if (CollectionUtil.isNotEmpty(roles)) {
            Set<String> perms = permissionService.getRolePermsFormCache(roles);
            userInfoVO.setPerms(perms);
        }

        return userInfoVO;
    }

    /**
     * Logout the current user.
     *
     * @return true if successfully logged out, false otherwise
     */
    @Override
    public boolean logout() {
        String jti = SecurityUtils.getJti();
        Optional<Long> expireTimeOpt = Optional.ofNullable(SecurityUtils.getExp()); // Use Optional to handle possible null values

        long currentTimeInSeconds = System.currentTimeMillis() / 1000; // Current time (in seconds)

        expireTimeOpt.ifPresent(expireTime -> {
            if (expireTime > currentTimeInSeconds) {
                // Token not expired, add to cache as blacklist with remaining valid time of the token
                long remainingTimeInSeconds = expireTime - currentTimeInSeconds;
                redisTemplate.opsForValue().set(RedisConstants.TOKEN_BLACKLIST_PREFIX + jti, "", remainingTimeInSeconds, TimeUnit.SECONDS);
            }
        });

        if (expireTimeOpt.isEmpty()) {
            // Token never expires, add to blacklist permanently
            redisTemplate.opsForValue().set(RedisConstants.TOKEN_BLACKLIST_PREFIX + jti, "");
        }

        return true;
    }

    /**
     * Register a new user.
     *
     * @param userRegisterForm User registration form object
     * @return true if registration is successful, false otherwise
     */
    @Override
    public boolean registerUser(UserRegisterForm userRegisterForm) {

        String mobile = userRegisterForm.getMobile();
//        String code = userRegisterForm.getCode();

        // Validate verification code
//        String cacheCode = redisTemplate.opsForValue().get(RedisConstants.REGISTER_SMS_CODE_PREFIX + mobile);
//        if (!StrUtil.equals(code, cacheCode)) {
//            log.warn("Verification code does not match or does not exist: {}", mobile);
//            return false; // Return false if verification code does not match or does not exist
//        }

        // Validation passed, delete verification code
        // redisTemplate.delete(RedisConstants.REGISTER_SMS_CODE_PREFIX + mobile);

        // Check if mobile number is already registered
        long count = this.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getMobile, mobile)
                .or()
                .eq(SysUser::getUsername, mobile)
        );
        Assert.isTrue(count == 0, "Mobile number already registered");

        SysUser entity = new SysUser();
        entity.setUsername(mobile);
        entity.setMobile(mobile);
        entity.setStatus(GlobalConstants.STATUS_YES);

        // Set default encrypted password
        String defaultEncryptPwd = passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD);
        entity.setPassword(defaultEncryptPwd);

        // Save user and return the result
        return this.save(entity);
    }

    /**
     * Get user profile information.
     *
     * @return {@link UserProfileVO}
     */
    @Override
    public UserProfileVO getUserProfile() {
        Long userId = SecurityUtils.getUserId();
        // Retrieve user profile information
        UserProfileBO userProfileBO = this.baseMapper.getUserProfile(userId);
        return userConverter.userProfileBo2Vo(userProfileBO);
    }

}
