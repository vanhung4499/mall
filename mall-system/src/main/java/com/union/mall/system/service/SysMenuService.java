package com.union.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.entity.SysMenu;
import com.union.mall.system.model.form.MenuForm;
import com.union.mall.system.model.query.MenuQuery;
import com.union.mall.system.model.vo.MenuVO;
import com.union.mall.system.model.vo.RouteVO;

import java.util.List;

/**
 * Menu service interface
 *
 * @author vanhung4499
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * Retrieve a list of menus for display in a table.
     *
     * @param queryParams Query parameters for filtering menus
     * @return List of menus
     */
    List<MenuVO> listMenus(MenuQuery queryParams);

    /**
     * Retrieve a dropdown list of menus.
     *
     * @return List of options representing menus
     */
    List<Option> listMenuOptions();

    /**
     * Save a new menu.
     *
     * @param menu Menu form data
     * @return True if the menu was successfully saved, false otherwise
     */
    boolean saveMenu(MenuForm menu);

    /**
     * Retrieve a list of routes.
     *
     * @return List of route information
     */
    List<RouteVO> listRoutes();

    /**
     * Update the visibility status of a menu.
     *
     * @param menuId  Menu ID
     * @param visible Visibility status (1 -> visible; 2 -> hidden)
     * @return True if the visibility status was successfully updated, false otherwise
     */
    boolean updateMenuVisible(Long menuId, Integer visible);

    /**
     * Retrieve menu form data by its ID.
     *
     * @param id Menu ID
     * @return Menu form data
     */
    MenuForm getMenuForm(Long id);

    /**
     * Delete a menu by its ID.
     *
     * @param id Menu ID
     * @return True if the menu was successfully deleted, false otherwise
     */
    boolean deleteMenu(Long id);
}
