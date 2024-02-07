package com.hnv99.mall.dao;

import com.hnv99.mall.model.UmsAdminRoleRelation;
import com.hnv99.mall.model.UmsResource;
import com.hnv99.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminRoleRelationDao {
    /**
     * Bulk insert user-role relationships
     */
    int insertList(@Param("list") List<UmsAdminRoleRelation> adminRoleRelationList);

    /**
     * Get all roles for a user
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * Get all accessible resources for a user
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * Get user ID list related to a resource
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
