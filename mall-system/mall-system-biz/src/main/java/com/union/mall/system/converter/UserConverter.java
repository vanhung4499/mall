package com.union.mall.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.system.model.bo.UserBO;
import com.union.mall.system.model.bo.UserFormBO;
import com.union.mall.system.model.bo.UserProfileBO;
import com.union.mall.system.model.entity.SysUser;
import com.union.mall.system.model.form.UserForm;
import com.union.mall.system.model.vo.UserImportVO;
import com.union.mall.system.model.vo.UserInfoVO;
import com.union.mall.system.model.vo.UserPageVO;
import com.union.mall.system.model.vo.UserProfileVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({
            @Mapping(target = "genderLabel", expression = "java(com.union.mall.common.base.IBaseEnum.getLabelByValue(bo.getGender(), com.union.mall.common.enums.GenderEnum.class))")
    })
    UserPageVO bo2Vo(UserBO bo);

    Page<UserPageVO> bo2Vo(Page<UserBO> bo);

    UserForm bo2Form(UserFormBO bo);

    UserForm entity2Form(SysUser entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SysUser form2Entity(UserForm entity);

    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    UserInfoVO entity2UserInfoVo(SysUser entity);

    SysUser importVo2Entity(UserImportVO vo);

    @Mappings({
            @Mapping(target = "genderLabel", expression = "java(com.union.mall.common.base.IBaseEnum.getLabelByValue(bo.getGender(), com.union.mall.common.enums.GenderEnum.class))")
    })
    UserProfileVO userProfileBo2Vo(UserProfileBO bo);
}
