package com.union.mall.system.controller;

import com.union.mall.common.core.result.Result;
import com.union.mall.common.web.annotation.PreventDuplicateResubmit;
import com.union.mall.system.model.form.DeptForm;
import com.union.mall.system.model.query.DeptQuery;
import com.union.mall.system.model.vo.DeptVO;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.service.SysDeptService;
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
@Tag(name = "04. Department API")
@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @Operation(summary = "Get Department List")
    @GetMapping
    public Result<List<DeptVO>> listDepartments(
            @ParameterObject DeptQuery queryParams
    ) {
        List<DeptVO> list = deptService.listDepartments(queryParams);
        return Result.success(list);
    }

    @Operation(summary = "Get Department Dropdown Options")
    @GetMapping("/options")
    public Result<List<Option>> listDeptOptions() {
        List<Option> list = deptService.listDeptOptions();
        return Result.success(list);
    }

    @Operation(summary = "Get Department Form Data")
    @GetMapping("/{deptId}/form")
    public Result<DeptForm> getDeptForm(
            @Parameter(description ="Department ID") @PathVariable Long deptId
    ) {
        DeptForm deptForm = deptService.getDeptForm(deptId);
        return Result.success(deptForm);
    }

    @Operation(summary = "Add Department")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:add')")
    @PreventDuplicateResubmit
    public Result saveDept(
            @Valid @RequestBody DeptForm formData
    ) {
        Long id = deptService.saveDept(formData);
        return Result.success(id);
    }

    @Operation(summary = "Update Department")
    @PutMapping(value = "/{deptId}")
    @PreAuthorize("@ss.hasPerm('sys:dept:edit')")
    public Result updateDept(
            @PathVariable Long deptId,
            @Valid @RequestBody DeptForm formData
    ) {
        deptId = deptService.updateDept(deptId, formData);
        return Result.success(deptId);
    }

    @Operation(summary = "Delete Department")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dept:delete')")
    public Result deleteDepartments(
            @Parameter(description ="Department IDs, separated by commas") @PathVariable("ids") String ids
    ) {
        boolean result = deptService.deleteByIds(ids);
        return Result.judge(result);
    }

}
