package com.hnv99.mall.service;

import com.hnv99.mall.model.UmsAdmin;
import com.hnv99.mall.model.UmsResource;

import java.util.List;

public interface UmsAdminCacheService {
    /**
     * Delete backend user cache
     */
    void delAdmin(Long adminId);

    /**
     * Delete backend user resource list cache
     */
    void delResourceList(Long adminId);

    /**
     * Delete related backend user cache when role-related resource information changes
     */
    void delResourceListByRole(Long roleId);

    /**
     * Delete related backend user cache when role-related resource information changes
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * Delete resource project backend user cache when resource information changes
     */
    void delResourceListByResource(Long resourceId);

    /**
     * Get cached backend user information
     */
    UmsAdmin getAdmin(String username);

    /**
     * Set cached backend user information
     */
    void setAdmin(UmsAdmin admin);

    /**
     * Get cached backend user resource list
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * Set cached backend user resource list
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
