package com.union.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.common.web.model.Option;
import com.union.mall.pms.model.entity.PmsCategory;
import com.union.mall.pms.model.vo.CategoryVO;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface CategoryService extends IService<PmsCategory> {

    /**
     * Category list (tree structure).
     *
     * @param parentId Parent category ID
     * @return List of categories
     */
    List<CategoryVO> getCategoryList(Long parentId);

    /**
     * Category list (cascading options).
     *
     * @return List of category options
     */
    List<Option> getCategoryOptions();

    /**
     * Save (add/update) category.
     *
     * @param category Category object to save
     * @return ID of the saved category
     */
    Long saveCategory(PmsCategory category);

}
