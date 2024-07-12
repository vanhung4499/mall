package com.union.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.union.mall.system.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * Get the maximum data range data scope.
     *
     * @param roles Set of role codes
     * @return Maximum data range data scope
     */
    Integer getMaxDataRangeDataScope(Set<String> roles);
}
