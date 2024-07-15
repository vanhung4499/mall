package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.system.mapper.SysUserRoleMapper;
import com.union.mall.system.model.entity.SysUserRole;
import com.union.mall.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * Save user roles
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public boolean saveUserRoles(Long userId, List<Long> roleIds) {

        if (userId == null || CollectionUtil.isEmpty(roleIds)) {
            return false;
        }

        // Original user role ID collection
        List<Long> userRoleIds = this.list(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId))
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        // Add new user roles
        List<Long> saveRoleIds;
        if (CollectionUtil.isEmpty(userRoleIds)) {
            saveRoleIds = roleIds;
        } else {
            saveRoleIds = roleIds.stream()
                    .filter(roleId -> !userRoleIds.contains(roleId))
                    .collect(Collectors.toList());
        }

        List<SysUserRole> saveUserRoles = saveRoleIds
                .stream()
                .map(roleId -> new SysUserRole(userId, roleId))
                .collect(Collectors.toList());
        this.saveBatch(saveUserRoles);

        // Remove user roles
        if (CollectionUtil.isNotEmpty(userRoleIds)) {
            List<Long> removeRoleIds = userRoleIds.stream()
                    .filter(roleId -> !roleIds.contains(roleId))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(removeRoleIds)) {
                this.remove(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId)
                        .in(SysUserRole::getRoleId, removeRoleIds)
                );
            }
        }
        return true;
    }

    /**
     * Check if a role has assigned users
     *
     * @param roleId Role ID
     * @return true: assigned, false: not assigned
     */
    @Override
    public boolean hasAssignedUsers(Long roleId) {
        int count = this.baseMapper.countUsersForRole(roleId);
        return count > 0;
    }
}
