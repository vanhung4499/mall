package com.union.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.system.model.entity.SysRoleMenu;

import java.util.List;

/**
 * Service interface for role menu operations.
 *
 * @author vanhung4499
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * Retrieve a list of menu IDs associated with a specific role.
     *
     * @param roleId Role ID
     * @return List of menu IDs
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * Refresh permission cache for all roles.
     */
    void refreshRolePermsCache();

    /**
     * Refresh permission cache for a specific role identified by role code.
     *
     * @param roleCode Role code
     */
    void refreshRolePermsCache(String roleCode);

    /**
     * Refresh permission cache when modifying role codes from oldRoleCode to newRoleCode.
     *
     * @param oldRoleCode Old role code
     * @param newRoleCode New role code
     */
    void refreshRolePermsCache(String oldRoleCode, String newRoleCode);

}
