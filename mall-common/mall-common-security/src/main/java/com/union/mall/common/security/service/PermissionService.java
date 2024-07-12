package com.union.mall.common.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.union.mall.common.core.constant.RedisConstants;
import com.union.mall.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.*;

/**
 * Service class for Spring Security permission validation.
 * <p>
 * This class checks if the current logged-in user has the required permissions based on roles stored in Redis.
 * It supports wildcard matching for permissions and caches role permissions for efficient lookup.
 * </p>
 *
 * @author vanhung4499
 */
@Service("ss")
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Checks if the current logged-in user has the required permission.
     *
     * @param requiredPerm Required permission
     * @return Whether the user has the permission
     */
    public boolean hasPerm(String requiredPerm) {
        if (StrUtil.isBlank(requiredPerm)) {
            return false;
        }

        // Allow super administrators unrestricted access
        if (SecurityUtils.isRoot()) {
            return true;
        }

        // Get role codes of the current logged-in user
        Set<String> roleCodes = SecurityUtils.getRoles();
        if (CollectionUtil.isEmpty(roleCodes)) {
            return false;
        }

        // Retrieve role permissions from cache for all roles of the current user
        Set<String> rolePerms = this.getRolePermsFromCache(roleCodes);
        if (CollectionUtil.isEmpty(rolePerms)) {
            return false;
        }

        // Check if any role permission matches the required permission (supports wildcard matching)
        boolean hasPermission = rolePerms.stream()
                .anyMatch(rolePerm -> PatternMatchUtils.simpleMatch(rolePerm, requiredPerm));

        if (!hasPermission) {
            log.error("User does not have permission: {}", requiredPerm);
        }
        return hasPermission;
    }

    /**
     * Retrieves role permissions from cache based on role codes.
     *
     * @param roleCodes Role codes of the current user
     * @return Set of role permissions
     */
    public Set<String> getRolePermsFromCache(Set<String> roleCodes) {
        if (CollectionUtil.isEmpty(roleCodes)) {
            return Collections.emptySet();
        }

        Set<String> perms = new HashSet<>();
        List<Object> rolePermsList = redisTemplate.opsForHash().multiGet(RedisConstants.ROLE_PERMS_PREFIX, new ArrayList<>(roleCodes));

        for (Object rolePermsObj : rolePermsList) {
            if (rolePermsObj instanceof Set) {
                @SuppressWarnings("unchecked")
                Set<String> rolePerms = (Set<String>) rolePermsObj;
                perms.addAll(rolePerms);
            }
        }

        return perms;
    }
}
