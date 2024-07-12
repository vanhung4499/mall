package com.union.mall.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.system.model.entity.SysDict;
import com.union.mall.system.model.form.DictForm;
import com.union.mall.system.model.vo.DictPageVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> entity2Page(Page<SysDict> page);

    DictForm entity2Form(SysDict entity);

    @InheritInverseConfiguration(name="entity2Form")
    SysDict form2Entity(DictForm entity);
}
