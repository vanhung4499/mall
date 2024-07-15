package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.RedisConstants;
import com.union.mall.system.mapper.SysRoleMenuMapper;
import com.union.mall.system.model.bo.RolePermsBO;
import com.union.mall.system.model.entity.SysRoleMenu;
import com.union.mall.system.service.SysRoleMenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Initialize permission cache
     */
    @PostConstruct
    public void initRolePermsCache() {
        refreshRolePermsCache();
    }

    /**
     * Refresh permission cache
     */
    @Override
    public void refreshRolePermsCache() {
        // Clear permission cache
        redisTemplate.opsForHash().delete(RedisConstants.ROLE_PERMS_PREFIX, "*");

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(null);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                String roleCode = item.getRoleCode();
                Set<String> perms = item.getPerms();
                redisTemplate.opsForHash().put(RedisConstants.ROLE_PERMS_PREFIX, roleCode, perms);
            });
        }
    }

    /**
     * Refresh permission cache
     */
    @Override
    public void refreshRolePermsCache(String roleCode) {
        // Clear permission cache
        redisTemplate.opsForHash().delete(RedisConstants.ROLE_PERMS_PREFIX, roleCode);

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(roleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.ROLE_PERMS_PREFIX, roleCode, perms);
        }
    }

    /**
     * Refresh permission cache (called when role code changes)
     */
    @Override
    public void refreshRolePermsCache(String oldRoleCode, String newRoleCode) {
        // Clear old role permission cache
        redisTemplate.opsForHash().delete(RedisConstants.ROLE_PERMS_PREFIX, oldRoleCode);

        // Add new role permission cache
        List<RolePermsBO> list = this.baseMapper.getRolePermsList(newRoleCode);
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.ROLE_PERMS_PREFIX, newRoleCode, perms);
        }
    }

    /**
     * Get the menu ID collection owned by the role
     *
     * @param roleId Role ID
     * @return Menu ID collection
     */
    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        return this.baseMapper.listMenuIdsByRoleId(roleId);
    }
}

