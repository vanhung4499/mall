package com.union.mall.pms.converter;

import com.union.mall.pms.model.dto.SkuInfoDTO;
import com.union.mall.pms.model.entity.PmsSku;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SkuConverter {

    SkuInfoDTO entity2SkuInfoDto(PmsSku entity);

    List<SkuInfoDTO> entity2SkuInfoDto(List<PmsSku> list);
}
