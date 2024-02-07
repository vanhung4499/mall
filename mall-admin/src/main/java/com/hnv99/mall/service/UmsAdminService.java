package com.hnv99.mall.service;

import com.hnv99.mall.dto.UmsAdminParam;
import com.hnv99.mall.dto.UpdateAdminPasswordParam;
import com.hnv99.mall.model.UmsAdmin;
import com.hnv99.mall.model.UmsResource;
import com.hnv99.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsAdminService {
    /**
     * Get the backend administrator based on the username
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * Registration function
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * Login function
     * @param username Username
     * @param password Password
     * @return Generated JWT token
     */
    String login(String username,String password);

    /**
     * Function to refresh the token
     * @param oldToken Old token
     */
    String refreshToken(String oldToken);

    /**
     * Get the user based on the user ID
     */
    UmsAdmin getItem(Long id);

    /**
     * Query users by username or nickname with pagination
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * Modify the specified user's information
     */
    int update(Long id, UmsAdmin admin);

    /**
     * Delete the specified user
     */
    int delete(Long id);

    /**
     * Modify user-role relationships
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * Get the roles corresponding to the user
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * Get the accessible resources for the specified user
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * Modify password
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * Get user information
     */
    UserDetails loadUserByUsername(String username);

    /**
     * Get the cache service
     */
    UmsAdminCacheService getCacheService();
}
