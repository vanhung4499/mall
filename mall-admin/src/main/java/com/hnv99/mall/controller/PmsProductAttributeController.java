package com.hnv99.mall.controller;

import com.hnv99.mall.common.api.CommonPage;
import com.hnv99.mall.common.api.CommonResult;
import com.hnv99.mall.dto.PmsProductAttributeParam;
import com.hnv99.mall.dto.ProductAttrInfo;
import com.hnv99.mall.model.PmsProductAttribute;
import com.hnv99.mall.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "PmsProductAttributeController")
@Tag(name = "PmsProductAttributeController", description = "Product Attribute Management")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService productAttributeService;

    @ApiOperation("Get attribute list or parameter list by category")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0 for attribute, 1 for parameter", required = true, paramType = "query", dataType = "integer")})
    @GetMapping(value = "/list/{cid}")
    @ResponseBody
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable Long cid,
                                                                 @RequestParam(value = "type") Integer type,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductAttribute> productAttributeList = productAttributeService.getList(cid, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productAttributeList));
    }

    @ApiOperation("Add product attribute information")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.create(productAttributeParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Update product attribute information")
    @PatchMapping(value = "/update/{id}")
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.update(id, productAttributeParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Get single product attribute")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonResult<PmsProductAttribute> getItem(@PathVariable Long id) {
        PmsProductAttribute productAttribute = productAttributeService.getItem(id);
        return CommonResult.success(productAttribute);
    }

    @ApiOperation("Batch delete product attributes")
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = productAttributeService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Get product attributes and attribute categories by product category id")
    @GetMapping(value = "/attrInfo/{productCategoryId}")
    @ResponseBody
    public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId) {
        List<ProductAttrInfo> productAttrInfoList = productAttributeService.getProductAttrInfo(productCategoryId);
        return CommonResult.success(productAttrInfoList);
    }
}
