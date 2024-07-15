package com.union.mall.pms.api;

import com.union.mall.common.web.config.FeignDecoderConfig;
import com.union.mall.pms.model.dto.LockSkuDTO;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@FeignClient(value = "mall-pms", contextId = "sku", configuration = {FeignDecoderConfig.class})
public interface SkuFeignClient {

    /**
     * Retrieve SKU information by SKU ID
     */
    @GetMapping("/app-api/v1/skus/{skuId}")
    SkuInfoDTO getSkuInfo(@PathVariable Long skuId);

    /**
     * Retrieve a list of SKU information by list of SKU IDs
     *
     * @param skuIds List of SKU IDs
     * @return List of SKU information
     */
    @GetMapping("/app-api/v1/skus")
    List<SkuInfoDTO> getSkuInfoList(@RequestParam List<Long> skuIds);

    /**
     * Lock SKU stock
     *
     * @param orderToken  Order token
     * @param lockSkuList List of SKU DTOs to lock
     * @return Lock result
     */
    @PutMapping("/app-api/v1/skus/lock")
    boolean lockStock(@RequestParam String orderToken, @RequestBody List<LockSkuDTO> lockSkuList);

    /**
     * Unlock SKU stock
     *
     * @param orderSn Order number
     * @return Unlock result
     */
    @PutMapping("/app-api/v1/skus/unlock")
    boolean unlockStock(@RequestParam String orderSn);

    /**
     * Deduct stock for order items
     *
     * Deduct the stock quantity of specified order items.
     *
     * @param orderSn Order number
     * @return Deduction result
     */
    @PutMapping("/app-api/v1/skus/deduct")
    boolean deductStock(@RequestParam String orderSn);

}
