package com.hnv99.mall.service;

import com.hnv99.mall.model.UmsMenu;
import com.hnv99.mall.model.UmsResource;
import com.hnv99.mall.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleService {
    /**
     * Add role
     */
    int create(UmsRole role);

    /**
     * Modify role information
     */
    int update(Long id, UmsRole role);

    /**
     * Batch delete roles
     */
    int delete(List<Long> ids);

    /**
     * Get all role list
     */
    List<UmsRole> list();

    /**
     * Paginated retrieval of role list
     */
    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * Get corresponding menu according to admin ID
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * Get role related menu
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * Get role related resources
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * Assign menu to role
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * Assign resources to role
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
