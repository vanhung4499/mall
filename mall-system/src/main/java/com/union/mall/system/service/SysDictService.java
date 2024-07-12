package com.union.mall.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.entity.SysDict;
import com.union.mall.system.model.form.DictForm;
import com.union.mall.system.model.query.DictPageQuery;
import com.union.mall.system.model.vo.DictPageVO;

import java.util.List;

/**
 * Dictionary service interface
 *
 * @author vanhung4499
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * Retrieve a paginated list of dictionary items.
     *
     * @param queryParams Query parameters for pagination and filtering
     * @return Page of dictionary items
     */
    Page<DictPageVO> getDictPage(DictPageQuery queryParams);

    /**
     * Retrieve details of a dictionary item by its ID.
     *
     * @param id Dictionary item ID
     * @return Dictionary item form data
     */
    DictForm getDictForm(Long id);

    /**
     * Save a new dictionary item.
     *
     * @param dictForm Dictionary item form data
     * @return True if the dictionary item was successfully saved, false otherwise
     */
    boolean saveDict(DictForm dictForm);

    /**
     * Update an existing dictionary item.
     *
     * @param id       Dictionary item ID
     * @param dictForm Updated dictionary item form data
     * @return True if the dictionary item was successfully updated, false otherwise
     */
    boolean updateDict(Long id, DictForm dictForm);

    /**
     * Delete dictionary items by their IDs.
     *
     * @param idsStr Comma-separated string of dictionary item IDs
     * @return True if deletion was successful, false otherwise
     */
    boolean deleteDict(String idsStr);

    /**
     * Retrieve a list of options for a dictionary type.
     *
     * @param typeCode Dictionary type code
     * @return List of options for the specified dictionary type
     */
    List<Option> listDictOptions(String typeCode);

}
