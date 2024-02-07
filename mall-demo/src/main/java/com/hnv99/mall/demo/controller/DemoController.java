package com.hnv99.mall.demo.controller;

import com.hnv99.mall.common.api.CommonPage;
import com.hnv99.mall.common.api.CommonResult;
import com.hnv99.mall.demo.dto.PmsBrandDto;
import com.hnv99.mall.demo.service.DemoService;
import com.hnv99.mall.model.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This class is annotated with API documentation tags and marked as a Spring MVC Controller
@Api(tags = "DemoController", description = "Brand management sample interface")
@Controller
public class DemoController {
    // The service class 'DemoService' is automatically wired in by Spring
    @Autowired
    private DemoService demoService;

    // A logger is set up to log debug level messages
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    // This method retrieves all brands and maps to the GET request at '/brand/listAll'
    @ApiOperation(value = "Get all brand list")
    @GetMapping(value = "/brand/listAll")
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(demoService.listAllBrand());
    }

    // This method creates a brand and maps to the POST request at '/brand/create'
    @ApiOperation(value = "Create brand")
    @PostMapping(value = "/brand/create")
    @ResponseBody
    public CommonResult createBrand(@Validated @RequestBody PmsBrandDto pmsBrand) {
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("Operation failed");
            LOGGER.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    // This method updates a brand and maps to the POST request at '/brand/update/{id}'
    @ApiOperation(value = "Update brand")
    @PostMapping(value = "/brand/update/{id}")
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @Validated @RequestBody PmsBrandDto pmsBrandDto) {
        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("updateBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("Operation failed");
            LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    // This method deletes a brand and maps to the GET request at '/brand/delete/{id}'
    @ApiOperation(value = "Delete brand")
    @DeleteMapping(value = "/brand/delete/{id}")
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = demoService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("Operation failed");
        }
    }

    // This method retrieves a paginated list of brands and maps to the GET request at '/brand/list'
    @ApiOperation(value = "Get brand list with pagination")
    @GetMapping(value = "/brand/list")
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    // This method retrieves a specific brand by its ID and maps to the GET request at '/brand/{id}'
    @ApiOperation(value = "Get brand information by ID")
    @GetMapping(value = "/brand/{id}")
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(demoService.getBrand(id));
    }
}
