package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.SystemConstants;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.converter.RoleConverter;
import com.union.mall.system.mapper.SysRoleMapper;
import com.union.mall.system.model.entity.SysRole;
import com.union.mall.system.model.entity.SysRoleMenu;
import com.union.mall.system.model.form.RoleForm;
import com.union.mall.system.model.query.RolePageQuery;
import com.union.mall.system.model.vo.RolePageVO;
import com.union.mall.system.service.SysRoleMenuService;
import com.union.mall.system.service.SysRoleService;
import com.union.mall.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService roleMenuService;
    private final SysUserRoleService userRoleService;
    private final RoleConverter roleConverter;

    /**
     * Role pagination list
     *
     * @param queryParams Role query parameters
     * @return {@link Page<RolePageVO>} – Role pagination list
     */
    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery queryParams) {
        // Query parameters
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // Query data
        Page<SysRole> rolePage = this.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysRole>()
                        .and(StrUtil.isNotBlank(keywords),
                                wrapper ->
                                        wrapper.like(StrUtil.isNotBlank(keywords), SysRole::getName, keywords)
                                                .or()
                                                .like(StrUtil.isNotBlank(keywords), SysRole::getCode, keywords)
                        )
                        .ne(!SecurityUtils.isRoot(), SysRole::getCode, SystemConstants.ROOT_ROLE_CODE) // Non-super administrators do not display super administrator roles
        );

        // Entity conversion
        Page<RolePageVO> pageResult = roleConverter.entity2Page(rolePage);
        return pageResult;
    }

    /**
     * Role dropdown list
     *
     * @return {@link List<Option>} – Role dropdown list
     */
    @Override
    public List<Option> listRoleOptions() {
        // Query data
        List<SysRole> roleList = this.list(new LambdaQueryWrapper<SysRole>()
                .ne(!SecurityUtils.isRoot(), SysRole::getCode, SystemConstants.ROOT_ROLE_CODE)
                .select(SysRole::getId, SysRole::getName)
                .orderByAsc(SysRole::getSort)
        );

        // Entity conversion
        return roleConverter.entities2Options(roleList);
    }

    /**
     * Save role
     *
     * @param roleForm Role form data
     * @return {@link Boolean}
     */
    @Override
    public boolean saveRole(RoleForm roleForm) {

        Long roleId = roleForm.getId();

        // When editing a role, check if the role exists
        SysRole oldRole = null;
        if (roleId != null) {
            oldRole = this.getById(roleId);
            Assert.isTrue(oldRole != null, "Role does not exist");
        }

        String roleCode = roleForm.getCode();
        long count = this.count(new LambdaQueryWrapper<SysRole>()
                .ne(roleId != null, SysRole::getId, roleId)
                .and(wrapper ->
                        wrapper.eq(SysRole::getCode, roleCode).or().eq(SysRole::getName, roleForm.getName())
                ));
        Assert.isTrue(count == 0, "Role name or role code already exists, please modify and try again!");

        // Entity conversion
        SysRole role = roleConverter.form2Entity(roleForm);

        boolean result = this.saveOrUpdate(role);
        if (result) {
            // Check if the role code or status has been modified, if so, refresh the permission cache
            if (oldRole != null
                    && (
                    !StrUtil.equals(oldRole.getCode(), roleCode) ||
                            !ObjectUtil.equals(oldRole.getStatus(), roleForm.getStatus())
            )) {
                roleMenuService.refreshRolePermsCache(oldRole.getCode(), roleCode);
            }
        }
        return result;
    }

    /**
     * Get role form data
     *
     * @param roleId Role ID
     * @return {@link RoleForm} – Role form data
     */
    @Override
    public RoleForm getRoleForm(Long roleId) {
        SysRole entity = this.getById(roleId);
        return roleConverter.entity2Form(entity);
    }

    /**
     * Modify role status
     *
     * @param roleId Role ID
     * @param status Role status (1: enable; 0: disable)
     * @return {@link Boolean}
     */
    @Override
    public boolean updateRoleStatus(Long roleId, Integer status) {
        SysRole role = this.getById(roleId);
        Assert.isTrue(role != null, "Role does not exist");

        role.setStatus(status);
        boolean result = this.updateById(role);
        if (result) {
            // Refresh the role's permission cache
            roleMenuService.refreshRolePermsCache(role.getCode());
        }

        return result;
    }

    /**
     * Batch delete roles
     *
     * @param ids Role IDs, separated by commas (,)
     * @return
     */
    @Override
    public boolean deleteRoles(String ids) {
        List<Long> roleIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();

        for (Long roleId : roleIds) {
            SysRole role = this.getById(roleId);
            Assert.isTrue(role != null, "Role does not exist");

            // Check if the role is associated with any users
            boolean isRoleAssigned = userRoleService.hasAssignedUsers(roleId);
            Assert.isTrue(!isRoleAssigned, "Role【{}】is assigned to users, please unassign it before deleting", role.getName());

            boolean deleteResult = this.removeById(roleId);
            if (deleteResult) {
                // If deletion is successful, refresh the permission cache
                roleMenuService.refreshRolePermsCache(role.getCode());
            }
        }
        return true;
    }

    /**
     * Get the menu ID collection of the role
     *
     * @param roleId Role ID
     * @return Menu ID collection (including button permission IDs)
     */
    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        return roleMenuService.listMenuIdsByRoleId(roleId);
    }

    /**
     * Assign resource permissions to the role
     *
     * @param roleId  Role ID
     * @param menuIds Menu ID collection
     * @return {@link Boolean}
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
        SysRole role = this.getById(roleId);
        Assert.isTrue(role != null, "Role does not exist");

        // Delete role menus
        roleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        // Add role menus
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<SysRoleMenu> roleMenus = menuIds.stream()
                    .map(menuId -> new SysRoleMenu(roleId, menuId))
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenus);
        }

        // Refresh the role's permission cache
        roleMenuService.refreshRolePermsCache(role.getCode());

        return true;
    }

    /**
     * Get the maximum scope of data permissions
     *
     * @param roles
     * @return
     */
    @Override
    public Integer getMaxDataRangeDataScope(Set<String> roles) {
        Integer dataScope = this.baseMapper.getMaxDataRangeDataScope(roles);
        return dataScope;
    }

}
