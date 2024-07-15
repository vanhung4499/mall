package com.union.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.model.entity.SysDept;
import com.union.mall.system.model.form.DeptForm;
import com.union.mall.system.model.query.DeptQuery;
import com.union.mall.system.model.vo.DeptVO;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * Retrieve a list of departments.
     *
     * @param queryParams Query parameters
     * @return List of departments
     */
    List<DeptVO> listDepartments(DeptQuery queryParams);

    /**
     * Retrieve department tree options for dropdowns.
     *
     * @return List of department options
     */
    List<Option> listDeptOptions();

    /**
     * Save a new department.
     *
     * @param formData Department form data
     * @return ID of the newly saved department
     */
    Long saveDept(DeptForm formData);

    /**
     * Update an existing department.
     *
     * @param deptId   Department ID
     * @param formData Updated department form data
     * @return ID of the updated department
     */
    Long updateDept(Long deptId, DeptForm formData);

    /**
     * Delete departments by their IDs.
     *
     * @param ids Comma-separated string of department IDs
     * @return True if deletion was successful, false otherwise
     */
    boolean deleteByIds(String ids);

    /**
     * Retrieve details of a department by its ID.
     *
     * @param deptId Department ID
     * @return Department form data
     */
    DeptForm getDeptForm(Long deptId);
}