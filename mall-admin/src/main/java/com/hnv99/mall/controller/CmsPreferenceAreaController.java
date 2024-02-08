package com.hnv99.mall.controller;

import com.hnv99.mall.common.api.CommonResult;
import com.hnv99.mall.model.CmsPreferenceArea;
import com.hnv99.mall.service.CmsPreferenceAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(tags = "CmsPreferenceAreaController")
@Tag(name = "CmsPreferenceAreaController", description = "Product Preference Management")
@RequestMapping("/preferenceArea")
public class CmsPreferenceAreaController {
    @Autowired
    private CmsPreferenceAreaService preferenceAreaService;

    @ApiOperation("Get All Product Preferences")
    @GetMapping(value = "/listAll")
    @ResponseBody
    public CommonResult<List<CmsPreferenceArea>> listAll() {
        List<CmsPreferenceArea> preferenceAreaList = preferenceAreaService.listAll();
        return CommonResult.success(preferenceAreaList);
    }
}
