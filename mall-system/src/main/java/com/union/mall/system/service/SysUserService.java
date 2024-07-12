package com.union.mall.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.system.model.dto.UserAuthInfo;
import com.union.mall.system.model.entity.SysUser;
import com.union.mall.system.model.form.UserForm;
import com.union.mall.system.model.form.UserRegisterForm;
import com.union.mall.system.model.query.UserPageQuery;
import com.union.mall.system.model.vo.UserExportVO;
import com.union.mall.system.model.vo.UserInfoVO;
import com.union.mall.system.model.vo.UserPageVO;
import com.union.mall.system.model.vo.UserProfileVO;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * Get a paginated list of users.
     *
     * @param queryParams Query parameters for filtering and pagination
     * @return {@link IPage<UserPageVO>} - Paginated list of users
     */
    IPage<UserPageVO> getUserPage(UserPageQuery queryParams);

    /**
     * Get user form data by user ID.
     *
     * @param userId User ID
     * @return {@link UserForm} - User form data
     */
    UserForm getUserFormData(Long userId);

    /**
     * Save a new user.
     *
     * @param userForm User form object
     * @return {@link Boolean} - Indicates whether the operation was successful
     */
    boolean saveUser(UserForm userForm);

    /**
     * Update an existing user.
     *
     * @param userId   User ID
     * @param userForm User form object containing updated information
     * @return {@link Boolean} - Indicates whether the operation was successful
     */
    boolean updateUser(Long userId, UserForm userForm);

    /**
     * Delete users by their IDs.
     *
     * @param idsStr Comma-separated string of user IDs
     * @return {@link Boolean} - Indicates whether the operation was successful
     */
    boolean deleteUsers(String idsStr);

    /**
     * Update user password.
     *
     * @param userId   User ID
     * @param password New password
     * @return {@link Boolean} - Indicates whether the operation was successful
     */
    boolean updatePassword(Long userId, String password);

    /**
     * Retrieve authentication information by username.
     *
     * @param username Username
     * @return {@link UserAuthInfo} - Authentication information for the user
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * Get a list of users for export.
     *
     * @param queryParams Query parameters
     * @return {@link List<UserExportVO>} - List of users formatted for export
     */
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);

    /**
     * Retrieve current user information.
     *
     * @return {@link UserInfoVO} - Information about the current logged-in user
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * Logout the current user.
     *
     * @return {@link Boolean} - Indicates whether the logout operation was successful
     */
    boolean logout();

    /**
     * Register a new user.
     *
     * @param userRegisterForm User registration form object
     * @return {@link Boolean} - Indicates whether the user registration was successful
     */
    boolean registerUser(UserRegisterForm userRegisterForm);

    /**
     * Retrieve user profile information.
     *
     * @return {@link UserProfileVO} - User profile information
     */
    UserProfileVO getUserProfile();
}