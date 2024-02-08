package com.hnv99.mall.controller;

import com.hnv99.mall.common.api.CommonResult;
import com.hnv99.mall.model.PmsSkuStock;
import com.hnv99.mall.service.PmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "PmsSkuStockController")
@Tag(name = "PmsSkuStockController", description = "SKU Product Stock Management")
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Autowired
    private PmsSkuStockService skuStockService;

    @ApiOperation("Fuzzy Search SKU Stock by Product ID and SKU Code")
    @GetMapping(value = "/{pid}")
    @ResponseBody
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "keyword",required = false) String keyword) {
        List<PmsSkuStock> skuStockList = skuStockService.getList(pid, keyword);
        return CommonResult.success(skuStockList);
    }

    @ApiOperation("Batch Update SKU Stock Info")
    @PostMapping(value ="/update/{pid}")
    @ResponseBody
    public CommonResult update(@PathVariable Long pid,@RequestBody List<PmsSkuStock> skuStockList){
        int count = skuStockService.update(pid,skuStockList);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
}
