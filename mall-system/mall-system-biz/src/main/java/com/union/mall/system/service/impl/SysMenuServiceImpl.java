package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.SystemConstants;
import com.union.mall.system.enums.MenuTypeEnum;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.system.converter.MenuConverter;
import com.union.mall.system.mapper.SysMenuMapper;
import com.union.mall.system.model.bo.RouteBO;
import com.union.mall.system.model.entity.SysMenu;
import com.union.mall.system.model.form.MenuForm;
import com.union.mall.system.model.query.MenuQuery;
import com.union.mall.system.model.vo.MenuVO;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.vo.RouteVO;
import com.union.mall.system.service.SysMenuService;
import com.union.mall.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final MenuConverter menuConverter;
    private final SysRoleMenuService roleMenuService;

    /**
     * Menu List
     *
     * @param queryParams {@link MenuQuery}
     */
    @Override
    public List<MenuVO> listMenus(MenuQuery queryParams) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(queryParams.getKeywords()), SysMenu::getName, queryParams.getKeywords())
                .orderByAsc(SysMenu::getSort)
        );

        Set<Long> parentIds = menus.stream()
                .map(SysMenu::getParentId)
                .collect(Collectors.toSet());
        Set<Long> menuIds = menus.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toSet());

        // Get root node IDs
        List<Long> rootIds = parentIds.stream()
                .filter(id -> !menuIds.contains(id))
                .toList();

        // Use a recursive function to build the menu tree

        return rootIds.stream()
                .flatMap(rootId -> buildMenuTree(rootId, menus).stream())
                .collect(Collectors.toList());
    }

    /**
     * Recursively generate the menu list
     *
     * @param parentId Parent ID
     * @param menuList Menu list
     * @return Menu list
     */
    private List<MenuVO> buildMenuTree(Long parentId, List<SysMenu> menuList) {
        return CollectionUtil.emptyIfNull(menuList)
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(entity -> {
                    MenuVO menuVO = menuConverter.entity2Vo(entity);
                    List<MenuVO> children = buildMenuTree(entity.getId(), menuList);
                    menuVO.setChildren(children);
                    return menuVO;
                }).toList();
    }

    /**
     * Menu dropdown data
     */
    @Override
    public List<Option> listMenuOptions() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        return buildMenuOptions(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * Recursively generate the menu dropdown hierarchy list
     *
     * @param parentId Parent ID
     * @param menuList Menu list
     * @return Menu dropdown list
     */
    private List<Option> buildMenuOptions(Long parentId, List<SysMenu> menuList) {
        List<Option> menuOptions = new ArrayList<>();

        for (SysMenu menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                Option option = new Option(menu.getId(), menu.getName());
                List<Option> subMenuOptions = buildMenuOptions(menu.getId(), menuList);
                if (!subMenuOptions.isEmpty()) {
                    option.setChildren(subMenuOptions);
                }
                menuOptions.add(option);
            }
        }

        return menuOptions;
    }

    /**
     * Route list
     */
    @Override
    @Cacheable(cacheNames = "menu", key = "'routes'")
    public List<RouteVO> listRoutes() {
        List<RouteBO> menuList = this.baseMapper.listRoutes();
        return buildRoutes(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * Recursively generate the menu route hierarchy list
     *
     * @param parentId Parent ID
     * @param menuList Menu list
     * @return Route hierarchy list
     */
    private List<RouteVO> buildRoutes(Long parentId, List<RouteBO> menuList) {
        List<RouteVO> routeList = new ArrayList<>();

        for (RouteBO menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                RouteVO routeVO = toRouteVo(menu);
                List<RouteVO> children = buildRoutes(menu.getId(), menuList);
                if (!children.isEmpty()) {
                    routeVO.setChildren(children);
                }
                routeList.add(routeVO);
            }
        }

        return routeList;
    }

    /**
     * Create RouteVO from RouteBO
     */
    private RouteVO toRouteVo(RouteBO routeBO) {
        RouteVO routeVO = new RouteVO();
        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(routeBO.getPath(), '-'));  // Route name needs to be camel case with the first letter capitalized
        routeVO.setName(routeName); // Route transition based on name this.$router.push({name:xxx})
        routeVO.setPath(routeBO.getPath()); // Route transition based on path this.$router.push({path:xxx})
        routeVO.setRedirect(routeBO.getRedirect());
        routeVO.setComponent(routeBO.getComponent());

        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle(routeBO.getName());
        meta.setIcon(routeBO.getIcon());
        meta.setRoles(routeBO.getRoles());
        meta.setHidden(StatusEnum.DISABLE.getValue().equals(routeBO.getVisible()));
        // Whether to enable page caching for the menu
        if (MenuTypeEnum.MENU.equals(routeBO.getType())
                && ObjectUtil.equals(routeBO.getKeepAlive(), 1)) {
            meta.setKeepAlive(true);
        }
        // Whether to always show if there is only one child route under the directory
        if (MenuTypeEnum.CATALOG.equals(routeBO.getType())
                && ObjectUtil.equals(routeBO.getAlwaysShow(), 1)) {
            meta.setAlwaysShow(true);
        }

        routeVO.setMeta(meta);
        return routeVO;
    }

    /**
     * Save menu
     */
    @Override
    public boolean saveMenu(MenuForm menuForm) {
        String path = menuForm.getPath();
        MenuTypeEnum menuType = menuForm.getType();

        // If it is a directory
        if (menuType == MenuTypeEnum.CATALOG) {
            if (menuForm.getParentId() == 0 && !path.startsWith("/")) {
                menuForm.setPath("/" + path); // The top-level directory must start with /
            }
            menuForm.setComponent("Layout");
        }
        // If it is an external link
        else if (menuType == MenuTypeEnum.EXTLINK) {
            menuForm.setComponent(null);
        }

        SysMenu entity = menuConverter.form2Entity(menuForm);
        String treePath = generateMenuTreePath(menuForm.getParentId());
        entity.setTreePath(treePath);

        boolean result = this.saveOrUpdate(entity);
        if (result) {
            // Edit to refresh role permissions cache
            if (menuForm.getId() != null) {
                roleMenuService.refreshRolePermsCache();
            }
        }
        return result;
    }

    /**
     * Department path generation
     *
     * @param parentId Parent ID
     * @return Parent node path separated by English commas (,), eg: 1,2,3
     */
    private String generateMenuTreePath(Long parentId) {
        if (SystemConstants.ROOT_NODE_ID.equals(parentId)) {
            return String.valueOf(parentId);
        } else {
            SysMenu parent = this.getById(parentId);
            return parent != null ? parent.getTreePath() + "," + parent.getId() : null;
        }
    }

    /**
     * Modify menu display status
     *
     * @param menuId  Menu ID
     * @param visible Whether to display (1-> display; 2-> hide)
     * @return Whether successful
     */
    @Override
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        return this.update(new LambdaUpdateWrapper<SysMenu>()
                .eq(SysMenu::getId, menuId)
                .set(SysMenu::getVisible, visible)
        );
    }

    /**
     * Get menu form data
     *
     * @param id Menu ID
     * @return {@link MenuForm}
     */
    @Override
    public MenuForm getMenuForm(Long id) {
        SysMenu entity = this.getById(id);
        return menuConverter.entity2Form(entity);
    }

    /**
     * Delete menu
     *
     * @param id Menu ID
     * @return Whether successful
     */
    @Override
    public boolean deleteMenu(Long id) {
        boolean result = this.remove(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getId, id)
                .or()
                .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", id));

        // Refresh role permissions cache
        if (result) {
            roleMenuService.refreshRolePermsCache();
        }
        return result;
    }
}
