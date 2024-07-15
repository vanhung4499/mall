package com.union.mall.oms.converter;

import com.union.mall.oms.model.dto.CartItemDto;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface CartConverter {

    @Mappings({
            @Mapping(target = "skuId", source = "id"),
    })
    CartItemDto sku2CartItem(SkuInfoDTO skuInfo);

}