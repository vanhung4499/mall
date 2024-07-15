package com.union.mall.ums.converter;

import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.model.entity.UmsAddress;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface AddressConvert {

    MemberAddressDTO entity2Dto(UmsAddress entity);

    List<MemberAddressDTO> entity2Dto(List<UmsAddress> entities);
}
