package com.union.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.system.model.entity.SysUserRole;

import java.util.List;

/**
 * Service interface for user role operations.
 *
 * @author vanhung4499
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * Save roles for a user.
     *
     * @param userId User ID
     * @param roleIds List of role IDs to assign to the user
     * @return {@link Boolean} – Indicates whether the operation was successful
     */
    boolean saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * Check if a role has assigned users.
     *
     * @param roleId Role ID
     * @return {@link Boolean} – True if the role has assigned users; false otherwise
     */
    boolean hasAssignedUsers(Long roleId);
}