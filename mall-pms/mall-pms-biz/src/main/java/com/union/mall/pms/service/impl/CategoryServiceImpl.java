package com.union.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.GlobalConstants;
import com.union.mall.common.web.model.Option;
import com.union.mall.pms.mapper.PmsCategoryMapper;
import com.union.mall.pms.model.entity.PmsCategory;
import com.union.mall.pms.model.vo.CategoryVO;
import com.union.mall.pms.service.CategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements CategoryService {

    /**
     * Category list (tree structure)
     *
     * @param parentId Parent category ID
     * @return List of categories
     * @Cacheable(value = "pms", key = "'categoryList'")
     */
    @Override
    public List<CategoryVO> getCategoryList(Long parentId) {
        List<PmsCategory> categoryList = this.list(
                new LambdaQueryWrapper<PmsCategory>()
                        .eq(PmsCategory::getVisible, GlobalConstants.STATUS_YES)
                        .orderByDesc(PmsCategory::getSort)
        );
        List<CategoryVO> list = recursionTree(parentId != null ? parentId : 0L, categoryList);
        return list;
    }

    private static List<CategoryVO> recursionTree(Long parentId, List<PmsCategory> categoryList) {
        List<CategoryVO> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream()
                                .filter(category -> category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    CategoryVO categoryVO = new CategoryVO();
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<CategoryVO> children = recursionTree(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                }));
        return list;
    }

    /**
     * Category list (cascading)
     *
     * @return List of category options
     */
    @Override
    public List<Option> getCategoryOptions() {
        List<PmsCategory> categoryList = this.list(
                new LambdaQueryWrapper<PmsCategory>()
                        .eq(PmsCategory::getVisible, GlobalConstants.STATUS_YES)
                        .orderByAsc(PmsCategory::getSort)
        );
        List<Option> list = recursionCascade(0L, categoryList);
        return list;
    }

    private List<Option> recursionCascade(Long parentId, List<PmsCategory> categoryList) {
        List<Option> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream()
                                .filter(category -> category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    Option categoryVO = new Option<>(category.getId(), category.getName());
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<Option> children = recursionCascade(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                })
                );
        return list;
    }

    /**
     * Save or update a category
     *
     * @param category Category object to save or update
     * @return Category ID
     * @CacheEvict(value = "pms", key = "'categoryList'")
     */
    @CacheEvict(value = "pms", key = "'categoryList'")
    @Override
    public Long saveCategory(PmsCategory category) {
        this.saveOrUpdate(category);
        return category.getId();
    }
}
