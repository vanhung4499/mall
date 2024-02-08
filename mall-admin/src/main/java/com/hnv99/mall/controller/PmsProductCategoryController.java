package com.hnv99.mall.controller;

import com.hnv99.mall.common.api.CommonPage;
import com.hnv99.mall.common.api.CommonResult;
import com.hnv99.mall.dto.PmsProductCategoryParam;
import com.hnv99.mall.dto.PmsProductCategoryWithChildrenItem;
import com.hnv99.mall.model.PmsProductCategory;
import com.hnv99.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "PmsProductCategoryController")
@Tag(name = "PmsProductCategoryController", description = "Product Category Management")
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    private PmsProductCategoryService productCategoryService;

    @ApiOperation("Add Product Category")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonResult create(@Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = productCategoryService.create(productCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Update Product Category")
    @PatchMapping(value = "/update/{id}")
    @ResponseBody
    public CommonResult update(@PathVariable Long id,
                               @Validated
                               @RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = productCategoryService.update(id, productCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Get Product Categories by Page")
    @GetMapping(value = "/list/{parentId}")
    @ResponseBody
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductCategory> productCategoryList = productCategoryService.getList(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productCategoryList));
    }

    @ApiOperation("Get Product Category by Id")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
        PmsProductCategory productCategory = productCategoryService.getItem(id);
        return CommonResult.success(productCategory);
    }

    @ApiOperation("Delete Product Category")
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = productCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Update Navigation Bar Display Status")
    @PatchMapping(value = "/update/navStatus")
    @ResponseBody
    public CommonResult updateNavStatus(@RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
        int count = productCategoryService.updateNavStatus(ids, navStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Update Display Status")
    @PatchMapping(value = "/update/showStatus")
    @ResponseBody
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
        int count = productCategoryService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("Get All Primary Categories and Their Subcategories")
    @GetMapping(value = "/list/withChildren")
    @ResponseBody
    public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
        List<PmsProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
        return CommonResult.success(list);
    }
}

