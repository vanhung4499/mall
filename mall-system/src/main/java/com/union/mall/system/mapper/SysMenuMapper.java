package com.union.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.union.mall.system.model.bo.RouteBO;
import com.union.mall.system.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<RouteBO> listRoutes();
}
