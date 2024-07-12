package com.union.mall.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.entity.SysRole;
import com.union.mall.system.model.form.RoleForm;
import com.union.mall.system.model.query.RolePageQuery;
import com.union.mall.system.model.vo.RolePageVO;

import java.util.List;
import java.util.Set;

/**
 * Role Service Interface
 *
 * @author vanhung4499
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * Retrieve a paginated list of roles based on query parameters.
     *
     * @param queryParams Role query parameters
     * @return {@link Page<RolePageVO>} – Paginated list of roles
     */
    Page<RolePageVO> getRolePage(RolePageQuery queryParams);

    /**
     * Retrieve a list of roles for dropdown selection.
     *
     * @return {@link List<Option>} – List of roles for dropdown selection
     */
    List<Option> listRoleOptions();

    /**
     * Save a new role.
     *
     * @param roleForm Role form data
     * @return {@link Boolean} – Indicates whether the role was successfully saved
     */
    boolean saveRole(RoleForm roleForm);

    /**
     * Retrieve role form data by role ID.
     *
     * @param roleId Role ID
     * @return {@link RoleForm} – Role form data
     */
    RoleForm getRoleForm(Long roleId);

    /**
     * Update the status of a role (enable/disable).
     *
     * @param roleId Role ID
     * @param status Role status (1: enabled; 0: disabled)
     * @return {@link Boolean} – Indicates whether the role status was successfully updated
     */
    boolean updateRoleStatus(Long roleId, Integer status);

    /**
     * Delete roles in batch.
     *
     * @param ids Comma-separated role IDs
     * @return {@link Boolean} – Indicates whether the roles were successfully deleted
     */
    boolean deleteRoles(String ids);

    /**
     * Retrieve the list of menu IDs associated with a role, including button permissions.
     *
     * @param roleId Role ID
     * @return List of menu IDs (including button permission IDs)
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * Assign menu resources to a role.
     *
     * @param roleId Role ID
     * @param menuIds List of menu IDs to assign
     * @return {@link Boolean} – Indicates whether the menus were successfully assigned to the role
     */
    boolean assignMenusToRole(Long roleId, List<Long> menuIds);

    /**
     * Retrieve the maximum data scope permission range for the specified roles.
     *
     * @param roles Set of role codes
     * @return {@link Integer} – Maximum data scope permission range
     */
    Integer getMaxDataRangeDataScope(Set<String> roles);

}