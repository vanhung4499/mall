package com.union.mall.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.web.annotation.PreventDuplicateResubmit;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.form.RoleForm;
import com.union.mall.system.model.query.RolePageQuery;
import com.union.mall.system.model.vo.RolePageVO;
import com.union.mall.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "02. Role API")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @Operation(summary = "Role Pagination List")
    @GetMapping("/page")
    public PageResult<RolePageVO> getRolePage(
            @ParameterObject RolePageQuery queryParams
    ) {
        Page<RolePageVO> result = roleService.getRolePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "Role Dropdown List")
    @GetMapping("/options")
    public Result<List<Option>> listRoleOptions() {
        List<Option> list = roleService.listRoleOptions();
        return Result.success(list);
    }

    @Operation(summary = "Add Role")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    @PreventDuplicateResubmit
    public Result addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "Role Form Data")
    @GetMapping("/{roleId}/form")
    public Result<RoleForm> getRoleForm(
            @Parameter(description ="Role ID") @PathVariable Long roleId
    ) {
        RoleForm roleForm = roleService.getRoleForm(roleId);
        return Result.success(roleForm);
    }

    @Operation(summary = "Update Role")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    public Result updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "Delete Roles")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    public Result deleteRoles(
            @Parameter(description ="Delete roles, separated by commas if multiple") @PathVariable String ids
    ) {
        boolean result = roleService.deleteRoles(ids);
        return Result.judge(result);
    }

    @Operation(summary = "Update Role Status")
    @PutMapping(value = "/{roleId}/status")
    public Result updateRoleStatus(
            @Parameter(description ="Role ID") @PathVariable Long roleId,
            @Parameter(description ="Status (1: enabled; 0: disabled)") @RequestParam Integer status
    ) {
        boolean result = roleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @Operation(summary = "Get Menu IDs of Role")
    @GetMapping("/{roleId}/menuIds")
    public Result<List<Long>> getRoleMenuIds(
            @Parameter(description ="Role ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = roleService.getRoleMenuIds(roleId);
        return Result.success(menuIds);
    }

    @Operation(summary = "Assign Menus to Role")
    @PutMapping("/{roleId}/menus")
    public Result assignMenusToRole(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds
    ) {
        boolean result = roleService.assignMenusToRole(roleId, menuIds);
        return Result.judge(result);
    }
}
