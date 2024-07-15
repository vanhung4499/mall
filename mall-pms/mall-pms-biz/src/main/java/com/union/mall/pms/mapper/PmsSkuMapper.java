package com.union.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import com.union.mall.pms.model.entity.PmsSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    /**
     * Retrieve information about a product SKU
     *
     * @param skuId SKU ID
     * @return SKU information DTO
     */
    SkuInfoDTO getSkuInfo(Long skuId);
}
