package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.converter.DictTypeConverter;
import com.union.mall.system.mapper.SysDictTypeMapper;
import com.union.mall.system.model.entity.SysDict;
import com.union.mall.system.model.entity.SysDictType;
import com.union.mall.system.model.form.DictTypeForm;
import com.union.mall.system.model.query.DictTypePageQuery;
import com.union.mall.system.model.vo.DictTypePageVO;
import com.union.mall.system.service.SysDictService;
import com.union.mall.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictService dictItemService;
    private final DictTypeConverter dictTypeConverter;

    /**
     * Dictionary Pagination List
     *
     * @param queryParams Pagination Query Object
     * @return
     */
    @Override
    public Page<DictTypePageVO> getDictTypePage(DictTypePageQuery queryParams) {
        // Query parameters
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // Query data
        Page<SysDictType> dictTypePage = this.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysDictType>()
                        .like(StrUtil.isNotBlank(keywords), SysDictType::getName, keywords)
                        .or()
                        .like(StrUtil.isNotBlank(keywords), SysDictType::getCode, keywords)
                        .select(
                                SysDictType::getId,
                                SysDictType::getName,
                                SysDictType::getCode,
                                SysDictType::getStatus,
                                SysDictType::getRemark
                        )
        );

        // Entity conversion
        Page<DictTypePageVO> pageResult = dictTypeConverter.entity2Page(dictTypePage);
        return pageResult;
    }

    /**
     * Get Dictionary Type Form Details
     *
     * @param id Dictionary Type ID
     * @return
     */
    @Override
    public DictTypeForm getDictTypeForm(Long id) {
        // Get entity
        SysDictType entity = this.getOne(new LambdaQueryWrapper<SysDictType>()
                .eq(SysDictType::getId, id)
                .select(
                        SysDictType::getId,
                        SysDictType::getName,
                        SysDictType::getCode,
                        SysDictType::getStatus,
                        SysDictType::getRemark
                ));
        Assert.isTrue(entity != null, "Dictionary type does not exist");

        // Entity conversion
        DictTypeForm dictTypeForm = dictTypeConverter.entity2Form(entity);
        return dictTypeForm;
    }

    /**
     * Add Dictionary Type
     *
     * @param dictTypeForm
     * @return
     */
    @Override
    public boolean saveDictType(DictTypeForm dictTypeForm) {
        // Entity object conversion form -> entity
        SysDictType entity = dictTypeConverter.form2Entity(dictTypeForm);
        // Persist
        boolean result = this.save(entity);
        return result;
    }

    /**
     * Update Dictionary Type
     *
     * @param id Dictionary Type ID
     * @param dictTypeForm Dictionary Type Form
     * @return
     */
    @Override
    public boolean updateDictType(Long id, DictTypeForm dictTypeForm) {
        // Get dictionary type
        SysDictType sysDictType = this.getById(id);
        Assert.isTrue(sysDictType != null, "Dictionary type does not exist");

        SysDictType entity = dictTypeConverter.form2Entity(dictTypeForm);
        boolean result = this.updateById(entity);
        if (result) {
            // If dictionary type code changes, synchronize the type code of the dictionary items
            String oldCode = sysDictType.getCode();
            String newCode = dictTypeForm.getCode();
            if (!StrUtil.equals(oldCode, newCode)) {
                dictItemService.update(new LambdaUpdateWrapper<SysDict>()
                        .eq(SysDict::getTypeCode, oldCode)
                        .set(SysDict::getTypeCode, newCode)
                );
            }
        }
        return result;
    }

    /**
     * Delete Dictionary Types
     *
     * @param idsStr Dictionary Type IDs, separated by English commas (,)
     * @return
     */
    @Override
    @Transactional
    public boolean deleteDictTypes(String idsStr) {

        Assert.isTrue(StrUtil.isNotBlank(idsStr), "No data to delete");

        List ids = Arrays.asList(idsStr.split(","))
                .stream()
                .collect(Collectors.toList());

        // Delete dictionary data items
        List<String> dictTypeCodes = this.list(new LambdaQueryWrapper<SysDictType>()
                        .in(SysDictType::getId, ids)
                        .select(SysDictType::getCode))
                .stream()
                .map(dictType -> dictType.getCode())
                .collect(Collectors.toList()
                );
        if (CollectionUtil.isNotEmpty(dictTypeCodes)) {
            dictItemService.remove(new LambdaQueryWrapper<SysDict>()
                    .in(SysDict::getTypeCode, dictTypeCodes));
        }
        // Delete dictionary types
        boolean result = this.removeByIds(ids);
        return result;
    }

    /**
     * Get Dictionary Type Data Items
     *
     * @param typeCode
     * @return
     */
    @Override
    public List<Option> listDictItemsByTypeCode(String typeCode) {
        // Dictionary data items
        List<SysDict> dictItems = dictItemService.list(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getTypeCode, typeCode)
                .select(SysDict::getValue, SysDict::getName)
        );

        // Convert to dropdown data
        List<Option> options = CollectionUtil.emptyIfNull(dictItems)
                .stream()
                .map(dictItem -> new Option(dictItem.getValue(), dictItem.getName()))
                .collect(Collectors.toList());
        return options;
    }
}
