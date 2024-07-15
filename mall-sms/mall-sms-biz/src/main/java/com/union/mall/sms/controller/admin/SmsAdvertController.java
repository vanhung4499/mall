package com.union.mall.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.sms.model.entity.SmsAdvert;
import com.union.mall.sms.model.query.AdvertPageQuery;
import com.union.mall.sms.service.SmsAdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Marketing Advertisements")
@RestController
@RequestMapping("/api/v1/adverts")
@RequiredArgsConstructor
public class SmsAdvertController {

    private final SmsAdvertService smsAdvertService;

    @Operation(summary= "Advertisement Pagination List")
    @GetMapping("/page")
    public PageResult<SmsAdvert> getAdvertPage(AdvertPageQuery queryParams) {

        // Query parameters
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // Pagination query
        Page<SmsAdvert> result = smsAdvertService.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SmsAdvert>()
                        .like(StrUtil.isNotBlank(keywords), SmsAdvert::getTitle, keywords)
                        .orderByAsc(SmsAdvert::getSort)
        );
        return PageResult.success(result);
    }

    @Operation(summary= "Advertisement Details")
    @GetMapping("/{id}")
    public Result getAdvertDetail(
            @Parameter(name = "Advertisement ID") @PathVariable Long id
    ) {
        SmsAdvert advert = smsAdvertService.getById(id);
        return Result.success(advert);
    }

    @Operation(summary= "Add Advertisement")
    @PostMapping
    public Result addAvert(@RequestBody SmsAdvert advert) {
        boolean status = smsAdvertService.save(advert);
        return Result.judge(status);
    }

    @Operation(summary= "Update Advertisement")
    @PutMapping(value = "/{id}")
    public Result updateAdvert(
            @Parameter(name = "Advertisement ID") @PathVariable Long id,
            @RequestBody SmsAdvert advert) {
        boolean status = smsAdvertService.updateById(advert);
        return Result.judge(status);
    }

    @Operation(summary= "Delete Advertisements")
    @DeleteMapping("/{ids}")
    public Result deleteAdverts(@Parameter(name = "Advertisement IDs separated by commas") @PathVariable("ids") String ids) {
        boolean status = smsAdvertService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}
