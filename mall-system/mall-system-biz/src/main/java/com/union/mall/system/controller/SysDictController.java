package com.union.mall.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.web.annotation.PreventDuplicateResubmit;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.form.DictForm;
import com.union.mall.system.model.form.DictTypeForm;
import com.union.mall.system.model.query.DictPageQuery;
import com.union.mall.system.model.query.DictTypePageQuery;
import com.union.mall.system.model.vo.DictPageVO;
import com.union.mall.system.model.vo.DictTypePageVO;
import com.union.mall.system.service.SysDictService;
import com.union.mall.system.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "05. Dictionary API")
@RestController
@RequestMapping("/api/v1/dict")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService dictService;
    private final SysDictTypeService dictTypeService;

    @Operation(summary = "Dictionary Page List")
    @GetMapping("/page")
    public PageResult<DictPageVO> getDictPage(
            @ParameterObject DictPageQuery queryParams
    ) {
        Page<DictPageVO> result = dictService.getDictPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "Dictionary Form Data")
    @GetMapping("/{id}/form")
    public Result<DictForm> getDictForm(
            @Parameter(description ="Dictionary ID") @PathVariable Long id
    ) {
        DictForm formData = dictService.getDictForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "Add Dictionary")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    @PreventDuplicateResubmit
    public Result saveDict(
            @RequestBody DictForm DictForm
    ) {
        boolean result = dictService.saveDict(DictForm);
        return Result.judge(result);
    }

    @Operation(summary = "Update Dictionary")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public Result updateDict(
            @PathVariable Long id,
            @RequestBody DictForm DictForm
    ) {
        boolean status = dictService.updateDict(id, DictForm);
        return Result.judge(status);
    }

    @Operation(summary = "Delete Dictionary")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public Result deleteDict(
            @Parameter(description ="Dictionary IDs, separated by commas") @PathVariable String ids
    ) {
        boolean result = dictService.deleteDict(ids);
        return Result.judge(result);
    }

    @Operation(summary = "Dictionary Dropdown List")
    @GetMapping("/options")
    public Result<List<Option>> listDictOptions(
            @Parameter(description ="Dictionary type code") @RequestParam String typeCode
    ) {
        List<Option> list = dictService.listDictOptions(typeCode);
        return Result.success(list);
    }

    /* Dictionary Type APIs */

    @Operation(summary = "Dictionary Type Page List")
    @GetMapping("/types/page")
    public PageResult<DictTypePageVO> getDictTypePage(
            @ParameterObject DictTypePageQuery queryParams
    ) {
        Page<DictTypePageVO> result = dictTypeService.getDictTypePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "Dictionary Type Form Data")
    @GetMapping("/types/{id}/form")
    public Result<DictTypeForm> getDictTypeForm(
            @Parameter(description ="Dictionary Type ID") @PathVariable Long id
    ) {
        DictTypeForm dictTypeForm = dictTypeService.getDictTypeForm(id);
        return Result.success(dictTypeForm);
    }

    @Operation(summary = "Add Dictionary Type")
    @PostMapping("/types")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:add')")
    @PreventDuplicateResubmit
    public Result saveDictType(@RequestBody DictTypeForm dictTypeForm) {
        boolean result = dictTypeService.saveDictType(dictTypeForm);
        return Result.judge(result);
    }

    @Operation(summary = "Update Dictionary Type")
    @PutMapping("/types/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:edit')")
    public Result updateDictType(@PathVariable Long id, @RequestBody DictTypeForm dictTypeForm) {
        boolean status = dictTypeService.updateDictType(id, dictTypeForm);
        return Result.judge(status);
    }

    @Operation(summary = "Delete Dictionary Types")
    @DeleteMapping("/types/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:delete')")
    public Result deleteDictTypes(
            @Parameter(description ="Dictionary Type IDs, separated by commas") @PathVariable String ids
    ) {
        boolean result = dictTypeService.deleteDictTypes(ids);
        return Result.judge(result);
    }

    @Operation(summary = "Get Items of Dictionary Type")
    @GetMapping("/types/{typeCode}/items")
    public Result<List<Option>> listDictTypeItems(
            @Parameter(description ="Dictionary Type code") @PathVariable String typeCode
    ) {
        List<Option> list = dictTypeService.listDictItemsByTypeCode(typeCode);
        return Result.success(list);
    }

}

