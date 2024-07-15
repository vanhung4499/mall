package com.union.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.union.mall.system.model.bo.RolePermsBO;
import com.union.mall.system.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import com.union.mall.system.model.entity.SysRole;

import java.util.List;
import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * Get the list of menu IDs owned by the role.
     *
     * @param roleId Role ID
     * @return List of menu IDs
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * Get the list of permissions and roles that possess those permissions.
     *
     * @param roleCode Role code
     * @return List of RolePermsBO objects
     */
    List<RolePermsBO> getRolePermsList(String roleCode);
}
