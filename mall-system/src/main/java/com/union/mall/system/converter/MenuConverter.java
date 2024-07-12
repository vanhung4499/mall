package com.union.mall.system.converter;

import com.union.mall.system.model.entity.SysMenu;
import com.union.mall.system.model.form.MenuForm;
import com.union.mall.system.model.vo.MenuVO;
import org.mapstruct.Mapper;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO entity2Vo(SysMenu entity);


    MenuForm entity2Form(SysMenu entity);

    SysMenu form2Entity(MenuForm menuForm);

}