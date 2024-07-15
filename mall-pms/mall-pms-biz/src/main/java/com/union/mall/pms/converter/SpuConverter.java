package com.union.mall.pms.converter;

import com.union.mall.pms.model.entity.PmsSpu;
import com.union.mall.pms.model.form.PmsSpuForm;
import com.union.mall.pms.model.vo.SeckillingSpuVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpuConverter {

    @Mappings({
            @Mapping(target = "album", source = "subPicUrls")
    })
    PmsSpu form2Entity(PmsSpuForm form);

    @InheritInverseConfiguration(name="form2Entity")
    PmsSpuForm entity2Form(PmsSpu entity);

    SeckillingSpuVO entity2SeckillingVO(PmsSpu entity);

    List<SeckillingSpuVO> entity2SeckillingVO(List<PmsSpu> entities);

}
