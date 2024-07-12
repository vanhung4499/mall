package com.union.mall.system.service;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.entity.SysDictType;
import com.union.mall.system.model.form.DictTypeForm;
import com.union.mall.system.model.query.DictTypePageQuery;
import com.union.mall.system.model.vo.DictTypePageVO;

import java.util.List;

/**
 * Dictionary type service interface
 *
 * This interface defines operations related to dictionary types.
 *
 * @author haoxr
 * @since 2023/3/4
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * Retrieve a paginated list of dictionary types.
     *
     * @param queryParams Query parameters for pagination and filtering
     * @return Page of dictionary type information
     */
    Page<DictTypePageVO> getDictTypePage(DictTypePageQuery queryParams);

    /**
     * Retrieve details of a dictionary type by its ID.
     *
     * @param id Dictionary type ID
     * @return Dictionary type form data
     */
    DictTypeForm getDictTypeForm(Long id);

    /**
     * Save a new dictionary type.
     *
     * @param dictTypeForm Dictionary type form data
     * @return True if the dictionary type was successfully saved, false otherwise
     */
    boolean saveDictType(DictTypeForm dictTypeForm);

    /**
     * Update an existing dictionary type.
     *
     * @param id           Dictionary type ID
     * @param dictTypeForm Updated dictionary type form data
     * @return True if the dictionary type was successfully updated, false otherwise
     */
    boolean updateDictType(Long id, DictTypeForm dictTypeForm);

    /**
     * Delete dictionary types by their IDs.
     *
     * @param idsStr Comma-separated string of dictionary type IDs
     * @return True if deletion was successful, false otherwise
     */
    boolean deleteDictTypes(String idsStr);

    /**
     * Retrieve data items associated with a dictionary type.
     *
     * @param typeCode Dictionary type code
     * @return List of options representing dictionary items for the specified type
     */
    List<Option> listDictItemsByTypeCode(String typeCode);

}
