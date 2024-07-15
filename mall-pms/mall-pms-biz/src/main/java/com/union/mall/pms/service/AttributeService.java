package com.union.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.pms.model.entity.PmsCategoryAttribute;
import com.union.mall.pms.model.form.PmsCategoryAttributeForm;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface AttributeService extends IService<PmsCategoryAttribute> {

    /**
     * Batch save product attributes.
     *
     * @param formData Attribute form data
     * @return true if successfully saved, false otherwise
     */
    boolean saveBatch(PmsCategoryAttributeForm formData);
}
