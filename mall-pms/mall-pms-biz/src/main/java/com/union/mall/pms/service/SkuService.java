package com.union.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.pms.model.dto.LockSkuDTO;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import com.union.mall.pms.model.entity.PmsSku;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface SkuService extends IService<PmsSku> {

    /**
     * Get SKU inventory information.
     *
     * @param skuId SKU ID
     * @return SKU inventory information
     */
    SkuInfoDTO getSkuInfo(Long skuId);

    /**
     * Get a list of SKU inventory information.
     *
     * @param skuIds List of SKU IDs
     * @return List of SKU inventory information
     */
    List<SkuInfoDTO> listSkuInfos(List<Long> skuIds);

    /**
     * Validate and lock inventory.
     *
     * @param orderToken  Temporary order token (order not created yet)
     * @param lockSkuList List of SKU inventory information to lock
     * @return `true` if successful, `false` otherwise
     */
    boolean lockStock(String orderToken, List<LockSkuDTO> lockSkuList);

    /**
     * Unlock inventory.
     *
     * @param orderSn Order serial number
     * @return `true` if successful, `false` otherwise
     */
    boolean unlockStock(String orderSn);

    /**
     * Deduct inventory.
     *
     * @param orderSn Order serial number
     * @return `true` if successful, `false` otherwise
     */
    boolean deductStock(String orderSn);
}
