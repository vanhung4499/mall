package com.union.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.pms.constant.ProductConstants;
import com.union.mall.pms.converter.SkuConverter;
import com.union.mall.pms.mapper.PmsSkuMapper;
import com.union.mall.pms.model.dto.LockSkuDTO;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import com.union.mall.pms.model.entity.PmsSku;
import com.union.mall.pms.service.SkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements SkuService {

    private final RedisTemplate redisTemplate;
    private final SkuConverter skuConverter;

    /**
     * Get SKU information
     *
     * @param skuId SKU ID
     * @return SKU information
     */
    @Override
    public SkuInfoDTO getSkuInfo(Long skuId) {
        return this.baseMapper.getSkuInfo(skuId);
    }

    /**
     * Get a list of SKU information
     *
     * @param skuIds List of SKU IDs
     * @return List of SKU information
     */
    @Override
    public List<SkuInfoDTO> listSkuInfos(List<Long> skuIds) {
        List<PmsSku> list = this.list(new LambdaQueryWrapper<PmsSku>().in(PmsSku::getId, skuIds));
        return skuConverter.entity2SkuInfoDto(list);
    }

    /**
     * Verify and lock stock
     *
     * @param orderToken  Order temporary token (order not yet created)
     * @param lockSkuList List of locked SKU DTOs
     * @return true/false
     */
    @Override
    @Transactional
    public boolean lockStock(String orderToken, List<LockSkuDTO> lockSkuList) {
        log.info("Locking stock for order ({}): {}", orderToken, JSONUtil.toJsonStr(lockSkuList));
        Assert.isTrue(CollectionUtil.isNotEmpty(lockSkuList), "Order ({}) does not contain any items", orderToken);

        // Check stock availability and lock
        for (LockSkuDTO lockedSku : lockSkuList) {
            Integer quantity = lockedSku.getQuantity(); // Quantity of items in the order
            // Sufficient stock check
            boolean lockResult = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .setSql("locked_stock = locked_stock + " + quantity) // Update locked stock count
                    .eq(PmsSku::getId, lockedSku.getSkuId())
                    .apply("stock - locked_stock >= {0}", quantity) // Remaining stock >= order quantity
            );
            Assert.isTrue(lockResult, "Insufficient stock for SKU");
        }

        // Cache locked SKUs in Redis (for future use: 1. Unlock stock on order cancellation; 2. Deduct stock on order payment)
        redisTemplate.opsForValue().set(ProductConstants.LOCKED_SKUS_PREFIX + orderToken, lockSkuList);
        return true;
    }

    /**
     * Unlock stock
     *
     * @param orderSn Order number
     * @return true/false
     */
    @Override
    @Transactional
    public boolean unlockStock(String orderSn) {
        List<LockSkuDTO> lockedSkus = (List<LockSkuDTO>) redisTemplate.opsForValue().get(ProductConstants.LOCKED_SKUS_PREFIX + orderSn);
        log.info("Releasing locked stock for order ({}): {}", orderSn, JSONUtil.toJsonStr(lockedSkus));

        // Stock already released
        if (CollectionUtil.isEmpty(lockedSkus)) {
            return true;
        }

        // Unlock stock
        for (LockSkuDTO lockedSku : lockedSkus) {
            boolean unlockResult = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .setSql("locked_stock = locked_stock - " + lockedSku.getQuantity())
                    .eq(PmsSku::getId, lockedSku.getSkuId())
            );
            Assert.isTrue(unlockResult, "Failed to unlock stock for SKU");
        }
        // Remove locked SKUs from Redis
        redisTemplate.delete(ProductConstants.LOCKED_SKUS_PREFIX + orderSn);
        return true;
    }

    /**
     * Deduct stock
     *
     * @param orderSn Order number
     * @return true/false
     */
    @Override
    @Transactional
    public boolean deductStock(String orderSn) {
        // Get locked SKUs from order submission
        List<LockSkuDTO> lockedSkus = (List<LockSkuDTO>) redisTemplate.opsForValue().get(ProductConstants.LOCKED_SKUS_PREFIX + orderSn);
        log.info("Order ({}) payment successful, deducting order stock: {}", orderSn, JSONUtil.toJsonStr(lockedSkus));

        Assert.isTrue(CollectionUtil.isNotEmpty(lockedSkus), "Failed to deduct stock: Order ({}) does not contain any items");

        for (LockSkuDTO lockedSku : lockedSkus) {
            boolean deductResult = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .setSql("stock = stock - " + lockedSku.getQuantity())
                    .setSql("locked_stock = locked_stock - " + lockedSku.getQuantity())
                    .eq(PmsSku::getId, lockedSku.getSkuId())
            );
            Assert.isTrue(deductResult, "Failed to deduct stock for SKU");
        }

        // Remove locked SKUs from the order
        redisTemplate.delete(ProductConstants.LOCKED_SKUS_PREFIX + orderSn);
        return true;
    }
}
