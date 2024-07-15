package com.union.mall.sms.controller.app;

import com.union.mall.common.core.result.Result;
import com.union.mall.sms.model.vo.BannerVO;
import com.union.mall.sms.service.SmsAdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "App-Marketing Advertisements")
@RestController
@RequestMapping("/app-api/v1/adverts")
@Slf4j
@AllArgsConstructor
public class AdvertController {

    private SmsAdvertService smsAdvertService;

    @Operation(summary= "APP Home Page Banner List")
    @GetMapping("/banners")
    public Result<List<BannerVO>> getBannerList() {
        List<BannerVO> list = smsAdvertService.getBannerList();
        return Result.success(list);
    }
}
