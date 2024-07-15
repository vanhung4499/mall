package com.union.mall.system.converter;

import com.union.mall.system.model.entity.SysDept;
import com.union.mall.system.model.form.DeptForm;
import com.union.mall.system.model.vo.DeptVO;
import org.mapstruct.Mapper;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm entity2Form(SysDept entity);
    DeptVO entity2Vo(SysDept entity);

    SysDept form2Entity(DeptForm deptForm);

}