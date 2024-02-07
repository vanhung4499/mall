package com.hnv99.mall.dao;

import com.hnv99.mall.model.UmsMenu;
import com.hnv99.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleDao {
    /**
     * Get menu by backend user ID
     */
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);

    /**
     * Get menu by role ID
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * Get resources by role ID
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}

