package com.union.mall.ums.converter;

import com.union.mall.ums.dto.MemberAuthDTO;
import com.union.mall.ums.dto.MemberInfoDTO;
import com.union.mall.ums.dto.MemberRegisterDTO;
import com.union.mall.ums.model.entity.UmsMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface MemberConvert {
    @Mappings({
            @Mapping(target = "username", source = "openid")
    })
    MemberAuthDTO entity2OpenidAuthDTO(UmsMember entity);

    @Mappings({
            @Mapping(target = "username", source = "mobile")
    })
    MemberAuthDTO entity2MobileAuthDTO(UmsMember entity);

    MemberInfoDTO entity2MemberInfoDTO(UmsMember entity);

    UmsMember dto2Entity(MemberRegisterDTO memberRegisterDTO);
}
