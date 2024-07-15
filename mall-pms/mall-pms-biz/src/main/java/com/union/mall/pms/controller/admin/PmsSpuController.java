package com.union.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.pms.model.form.PmsSpuForm;
import com.union.mall.pms.model.query.SpuPageQuery;
import com.union.mall.pms.model.vo.PmsSpuDetailVO;
import com.union.mall.pms.model.vo.PmsSpuPageVO;
import com.union.mall.pms.service.SpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Product SPU API")
@RestController
@RequestMapping("/api/v1/spu")
@AllArgsConstructor
public class PmsSpuController {

    private SpuService spuService;

    @Operation(summary = "Product Pagination List")
    @GetMapping("/page")
    public PageResult listPagedSpu(SpuPageQuery queryParams) {
        IPage<PmsSpuPageVO> result = spuService.listPagedSpu(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "Product Details")
    @GetMapping("/{id}/detail")
    public Result detail(@Parameter(name = "Product ID") @PathVariable Long id) {
        PmsSpuDetailVO pmsSpuDetailVO = spuService.getSpuDetail(id);
        return Result.success(pmsSpuDetailVO);
    }

    @Operation(summary = "Add Product")
    @PostMapping
    public Result addSpu(@RequestBody PmsSpuForm formData) {
        boolean result = spuService.addSpu(formData);
        return Result.judge(result);
    }

    @Operation(summary = "Update Product")
    @PutMapping(value = "/{id}")
    public Result updateSpuById(
            @Parameter(name = "Product ID") @PathVariable Long id,
            @RequestBody PmsSpuForm formData
    ) {
        boolean result = spuService.updateSpuById(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "Delete Product")
    @DeleteMapping("/{ids}")
    public Result delete(
            @Parameter(name = "Product IDs, separated by commas") @PathVariable String ids
    ) {
        boolean result = spuService.removeBySpuIds(ids);
        return Result.judge(result);
    }
}
