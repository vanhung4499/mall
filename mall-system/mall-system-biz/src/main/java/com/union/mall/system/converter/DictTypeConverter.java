package com.union.mall.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.system.model.entity.SysDictType;
import com.union.mall.system.model.form.DictTypeForm;
import com.union.mall.system.model.vo.DictTypePageVO;
import org.mapstruct.Mapper;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface DictTypeConverter {

    Page<DictTypePageVO> entity2Page(Page<SysDictType> page);

    DictTypeForm entity2Form(SysDictType entity);

    SysDictType form2Entity(DictTypeForm entity);
}
