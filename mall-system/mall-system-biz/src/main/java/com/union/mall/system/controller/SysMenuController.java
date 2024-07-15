package com.union.mall.system.controller;

import com.union.mall.common.core.result.Result;
import com.union.mall.common.web.annotation.PreventDuplicateResubmit;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.form.MenuForm;
import com.union.mall.system.model.query.MenuQuery;
import com.union.mall.system.model.vo.MenuVO;
import com.union.mall.system.model.vo.RouteVO;
import com.union.mall.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "03. Menu API")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    private final SysMenuService menuService;

    @Operation(summary = "Menu List")
    @GetMapping
    public Result<List<MenuVO>> listMenus(
            @ParameterObject MenuQuery queryParams
    ) {
        List<MenuVO> menuList = menuService.listMenus(queryParams);
        return Result.success(menuList);
    }

    @Operation(summary = "Menu Dropdown List")
    @GetMapping("/options")
    public Result listMenuOptions() {
        List<Option> menus = menuService.listMenuOptions();
        return Result.success(menus);
    }

    @Operation(summary = "Route List")
    @GetMapping("/routes")
    public Result<List<RouteVO>> listRoutes() {
        List<RouteVO> routeList = menuService.listRoutes();
        return Result.success(routeList);
    }

    @Operation(summary = "Menu Form Data")
    @GetMapping("/{id}/form")
    public Result<MenuForm> getMenuForm(
            @Parameter(description =  "Menu ID") @PathVariable Long id
    ) {
        MenuForm menu = menuService.getMenuForm(id);
        return Result.success(menu);
    }

    @Operation(summary = "Add Menu")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:menu:add')")
    @PreventDuplicateResubmit
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public Result addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "Update Menu")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:edit')")
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public Result updateMenu(
            @RequestBody MenuForm menuForm
    ) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "Delete Menu")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:delete')")
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public Result deleteMenu(
            @Parameter(description ="Menu ID, separated by commas if multiple") @PathVariable("id") Long id
    ) {
        boolean result = menuService.deleteMenu(id);
        return Result.judge(result);
    }

    @Operation(summary = "Update Menu Visibility")
    @PatchMapping("/{menuId}")
    public Result updateMenuVisible(
            @Parameter(description =  "Menu ID") @PathVariable Long menuId,
            @Parameter(description =  "Visibility Status (1: visible; 0: hidden)") Integer visible
    ) {
        boolean result = menuService.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }

}
