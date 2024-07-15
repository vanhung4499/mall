package com.union.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.pms.mapper.PmsCategoryAttributeMapper;
import com.union.mall.pms.model.entity.PmsCategoryAttribute;
import com.union.mall.pms.service.AttributeService;
import com.union.mall.pms.model.form.PmsCategoryAttributeForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
public class AttributeServiceImpl extends ServiceImpl<PmsCategoryAttributeMapper, PmsCategoryAttribute> implements AttributeService {

    /**
     * Batch save product attributes.
     *
     * @param formData Form data
     * @return true if successfully saved, false otherwise
     */
    @Override
    public boolean saveBatch(PmsCategoryAttributeForm formData) {
        Long categoryId = formData.getCategoryId();
        Integer attributeType = formData.getType();

        // Extract IDs from form data
        List<Long> formIds = formData.getAttributes().stream()
                .filter(item -> item.getId() != null)
                .map(PmsCategoryAttributeForm.Attribute::getId)
                .collect(Collectors.toList());

        // Query database IDs based on category ID and attribute type
        List<Long> dbIds = this.list(new LambdaQueryWrapper<PmsCategoryAttribute>()
                        .eq(PmsCategoryAttribute::getCategoryId, categoryId)
                        .eq(PmsCategoryAttribute::getType, attributeType)
                        .select(PmsCategoryAttribute::getId)).stream()
                .map(PmsCategoryAttribute::getId)
                .collect(Collectors.toList());

        // Delete IDs that are in database but not in form data
        if (CollectionUtil.isNotEmpty(dbIds)) {
            List<Long> rmIds = dbIds.stream()
                    .filter(id -> CollectionUtil.isEmpty(formIds) || !formIds.contains(id))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(rmIds)) {
                this.removeByIds(rmIds);
            }
        }

        // Prepare list of attributes to save or update
        List<PmsCategoryAttribute> attributeList = new ArrayList<>();

        formData.getAttributes().forEach(item -> {
            PmsCategoryAttribute attribute = PmsCategoryAttribute.builder()
                    .id(item.getId())
                    .categoryId(categoryId)
                    .type(attributeType)
                    .name(item.getName())
                    .build();
            attributeList.add(attribute);
        });

        // Save or update the attributes in batch
        boolean result = this.saveOrUpdateBatch(attributeList);
        return result;
    }
}
