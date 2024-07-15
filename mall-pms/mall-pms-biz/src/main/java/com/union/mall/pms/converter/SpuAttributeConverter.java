package com.union.mall.pms.converter;

import com.union.mall.pms.model.entity.PmsSpuAttribute;
import com.union.mall.pms.model.form.PmsSpuAttributeForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SpuAttributeConverter {

    @Mappings({
            @Mapping(target = "id",ignore = true)
    })
    PmsSpuAttribute form2Entity(PmsSpuAttributeForm form);

}
