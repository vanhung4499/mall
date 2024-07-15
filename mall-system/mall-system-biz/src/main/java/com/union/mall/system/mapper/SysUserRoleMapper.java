package com.union.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.union.mall.system.model.entity.SysUserRole;
import com.youlai.system.model.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * Get the number of users assigned to a role
     *
     * @param roleId Role ID
     * @return The number of users assigned to the role
     */
    int countUsersForRole(Long roleId);
}
