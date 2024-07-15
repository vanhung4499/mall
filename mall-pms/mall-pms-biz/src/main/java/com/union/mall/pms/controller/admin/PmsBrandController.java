package com.union.mall.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.pms.model.entity.PmsBrand;
import com.union.mall.pms.model.query.BrandPageQuery;
import com.union.mall.pms.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Brand API")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class PmsBrandController {

    private final BrandService brandService;

    @Operation(summary = "Brand Pagination List")
    @GetMapping("/pages")
    public PageResult getBrandPage(BrandPageQuery queryParams) {

        // Query parameters
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // Pagination query
        Page<PmsBrand> page = brandService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<PmsBrand>().like(StrUtil.isNotBlank(keywords), PmsBrand::getName, keywords)
                        .orderByDesc(PmsBrand::getCreateTime));
        return PageResult.success(page);
    }

    @Operation(summary = "Brand List")
    @GetMapping
    public Result getBrandList() {
        List<PmsBrand> list = brandService.list(new LambdaQueryWrapper<PmsBrand>()
                .select(PmsBrand::getId, PmsBrand::getName));
        return Result.success(list);
    }

    @Operation(summary = "Brand Details")
    @GetMapping("/{id}")
    public Result getBrandList(@PathVariable Integer id) {
        PmsBrand brand = brandService.getById(id);
        return Result.success(brand);
    }

    @Operation(summary = "Add Brand")
    @PostMapping
    public Result addBrand(@RequestBody PmsBrand brand) {
        boolean status = brandService.save(brand);
        return Result.judge(status);
    }

    @Operation(summary = "Update Brand")
    @PutMapping(value = "/{id}")
    public Result updateBrand(
            @PathVariable Long id,
            @RequestBody PmsBrand brand) {
        boolean status = brandService.updateById(brand);
        return Result.judge(status);
    }

    @Operation(summary = "Delete Brands")
    @DeleteMapping("/{ids}")
    public Result deleteBrands(@Parameter(name = "Brand IDs, separated by comma") @PathVariable("ids") String ids) {
        boolean status = brandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}
