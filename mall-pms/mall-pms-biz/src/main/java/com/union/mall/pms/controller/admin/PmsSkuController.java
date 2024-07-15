package com.union.mall.pms.controller.admin;

import com.union.mall.common.core.result.Result;
import com.union.mall.pms.model.entity.PmsSku;
import com.union.mall.pms.service.SkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Product SKU API")
@RestController
@RequestMapping("/api/v1/sku")
@RequiredArgsConstructor
public class PmsSkuController {
    private final SkuService skuService;

    @Operation(summary = "Product SKU Details")
    @GetMapping("/{skuId}")
    public Result getSkuDetail(
            @Parameter(name = "SkuId") @PathVariable Long skuId
    ) {
        PmsSku sku = skuService.getById(skuId);
        return Result.success(sku);
    }

    @Operation(summary = "Update SKU")
    @PutMapping(value = "/{skuId}")
    public Result updateSku(
            @Parameter(name = "SkuId") @PathVariable Long skuId,
            @RequestBody PmsSku sku
    ) {
        boolean result = skuService.updateById(sku);
        return Result.judge(result);
    }
}
