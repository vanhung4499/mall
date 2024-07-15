package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.converter.DictConverter;
import com.union.mall.system.mapper.SysDictMapper;
import com.union.mall.system.model.entity.SysDict;
import com.union.mall.system.model.form.DictForm;
import com.union.mall.system.model.query.DictPageQuery;
import com.union.mall.system.model.vo.DictPageVO;
import com.union.mall.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final DictConverter dictConverter;

    /**
     * Dictionary Data Item Pagination List
     *
     * @param queryParams Pagination query object
     * @return
     */
    @Override
    public Page<DictPageVO> getDictPage(DictPageQuery queryParams) {
        // Query parameters
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();
        String typeCode = queryParams.getTypeCode();

        // Query data
        Page<SysDict> dictItemPage = this.page(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysDict>()
                        .like(StrUtil.isNotBlank(keywords), SysDict::getName, keywords)
                        .eq(StrUtil.isNotBlank(typeCode), SysDict::getTypeCode, typeCode)
                        .select(SysDict::getId, SysDict::getName, SysDict::getValue, SysDict::getStatus)
        );

        // Entity conversion
        Page<DictPageVO> pageResult = dictConverter.entity2Page(dictItemPage);
        return pageResult;
    }

    /**
     * Dictionary Data Item Form Details
     *
     * @param id Dictionary data item ID
     * @return
     */
    @Override
    public DictForm getDictForm(Long id) {
        // Get entity
        SysDict entity = this.getOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getId, id)
                .select(
                        SysDict::getId,
                        SysDict::getTypeCode,
                        SysDict::getName,
                        SysDict::getValue,
                        SysDict::getStatus,
                        SysDict::getSort,
                        SysDict::getRemark
                ));
        Assert.isTrue(entity != null, "Dictionary data item does not exist");

        // Entity conversion
        DictForm dictForm = dictConverter.entity2Form(entity);
        return dictForm;
    }

    /**
     * Add Dictionary Data Item
     *
     * @param dictForm Dictionary data item form
     * @return
     */
    @Override
    public boolean saveDict(DictForm dictForm) {
        // Entity object conversion form -> entity
        SysDict entity = dictConverter.form2Entity(dictForm);
        // Persist
        boolean result = this.save(entity);
        return result;
    }

    /**
     * Update Dictionary Data Item
     *
     * @param id Dictionary data item ID
     * @param dictForm Dictionary data item form
     * @return
     */
    @Override
    public boolean updateDict(Long id, DictForm dictForm) {
        SysDict entity = dictConverter.form2Entity(dictForm);
        boolean result = this.updateById(entity);
        return result;
    }

    /**
     * Delete Dictionary Data Items
     *
     * @param idsStr Dictionary data item IDs, separated by English commas (,)
     * @return
     */
    @Override
    public boolean deleteDict(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "No data to delete");

        List<Long> ids = Arrays.asList(idsStr.split(","))
                .stream()
                .map(id -> Long.parseLong(id))
                .collect(Collectors.toList());

        // Delete dictionary data items
        boolean result = this.removeByIds(ids);
        return result;
    }

    /**
     * Get Dictionary Dropdown List
     *
     * @param typeCode
     * @return
     */
    @Override
    public List<Option> listDictOptions(String typeCode) {
        // Dictionary data items
        List<SysDict> dictList = this.list(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getTypeCode, typeCode)
                .select(SysDict::getValue, SysDict::getName)
        );

        // Convert to dropdown data
        List<Option> options = CollectionUtil.emptyIfNull(dictList)
                .stream()
                .map(dictItem -> new Option(dictItem.getValue(), dictItem.getName()))
                .collect(Collectors.toList());
        return options;
    }
}
