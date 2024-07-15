package com.union.mall.pms.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.web.model.Option;
import com.union.mall.pms.model.entity.PmsCategory;
import com.union.mall.pms.model.entity.PmsCategoryAttribute;
import com.union.mall.pms.model.vo.CategoryVO;
import com.union.mall.pms.service.AttributeService;
import com.union.mall.pms.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Product Categories")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class PmsCategoryController {

    private final CategoryService categoryService;
    private final AttributeService attributeService;

    @Operation(summary = "Product Category List")
    @GetMapping
    public Result<List<CategoryVO>> getCategoryList() {
        List<CategoryVO> list = categoryService.getCategoryList(null);
        return Result.success(list);
    }

    @Operation(summary = "Product Category Cascading List")
    @GetMapping("/options")
    public Result getCategoryOptions() {
        List<Option> list = categoryService.getCategoryOptions();
        return Result.success(list);
    }

    @Operation(summary = "Product Category Details")
    @GetMapping("/{id}")
    public Result detail(
            @Parameter(name = "Product Category ID") @PathVariable Long id
    ) {
        PmsCategory category = categoryService.getById(id);
        return Result.success(category);
    }

    @Operation(summary = "Add Product Category")
    @PostMapping
    public Result addCategory(@RequestBody PmsCategory category) {
        Long id = categoryService.saveCategory(category);
        return Result.success(id);
    }

    @Operation(summary = "Update Product Category")
    @PutMapping(value = "/{id}")
    public Result update(
            @Parameter(name = "Product Category ID") @PathVariable Long id,
            @RequestBody PmsCategory category
    ) {
        category.setId(id);
        id = categoryService.saveCategory(category);
        return Result.success(id);
    }

    @Operation(summary = "Delete Product Category")
    @DeleteMapping("/{ids}")
    @CacheEvict(value = "pms", key = "'categoryList'")
    public Result delete(@PathVariable String ids) {
        List<String> categoryIds = Arrays.asList(ids.split(","));
        attributeService.remove(new LambdaQueryWrapper<PmsCategoryAttribute>().in(CollectionUtil.isNotEmpty(categoryIds),
                PmsCategoryAttribute::getCategoryId, categoryIds));
        boolean result = categoryService.removeByIds(categoryIds);
        return Result.judge(result);
    }

    @Operation(summary = "Selective Update Product Category")
    @PatchMapping(value = "/{id}")
    @CacheEvict(value = "pms", key = "'categoryList'")
    public Result patch(@PathVariable Long id, @RequestBody PmsCategory category) {
        LambdaUpdateWrapper<PmsCategory> updateWrapper = new LambdaUpdateWrapper<PmsCategory>()
                .eq(PmsCategory::getId, id);
        updateWrapper.set(category.getVisible() != null, PmsCategory::getVisible, category.getVisible());
        boolean result = categoryService.update(updateWrapper);
        return Result.judge(result);
    }
}
