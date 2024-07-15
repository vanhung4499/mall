package com.union.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.SystemConstants;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.common.web.model.Option;
import com.union.mall.system.converter.DeptConverter;
import com.union.mall.system.mapper.SysDeptMapper;
import com.union.mall.system.model.entity.SysDept;
import com.union.mall.system.model.form.DeptForm;
import com.union.mall.system.model.query.DeptQuery;
import com.union.mall.system.model.vo.DeptVO;
import com.union.mall.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final DeptConverter deptConverter;

    /**
     * Department List
     */
    @Override
    public List<DeptVO> listDepartments(DeptQuery queryParams) {
        // Query parameters
        String keywords = queryParams.getKeywords();
        Integer status = queryParams.getStatus();

        // Query data
        List<SysDept> deptList = this.list(
                new LambdaQueryWrapper<SysDept>()
                        .like(StrUtil.isNotBlank(keywords), SysDept::getName, keywords)
                        .eq(status != null, SysDept::getStatus, status)
                        .orderByAsc(SysDept::getSort)
        );

        Set<Long> deptIds = deptList.stream()
                .map(SysDept::getId)
                .collect(Collectors.toSet());

        Set<Long> parentIds = deptList.stream()
                .map(SysDept::getParentId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, deptIds);

        List<DeptVO> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurDeptList(rootId, deptList));
        }
        return list;
    }

    /**
     * Recursively generate department tree list
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public List<DeptVO> recurDeptList(Long parentId, List<SysDept> deptList) {
        return deptList.stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .map(dept -> {
                    DeptVO deptVO = deptConverter.entity2Vo(dept);
                    List<DeptVO> children = recurDeptList(dept.getId(), deptList);
                    deptVO.setChildren(children);
                    return deptVO;
                }).collect(Collectors.toList());
    }

    /**
     * Department dropdown options
     *
     * @return List of department dropdown options
     */
    @Override
    public List<Option> listDeptOptions() {
        List<SysDept> deptList = this.list(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getStatus, StatusEnum.ENABLE.getValue())
                .select(SysDept::getId, SysDept::getParentId, SysDept::getName)
                .orderByAsc(SysDept::getSort)
        );

        Set<Long> parentIds = deptList.stream()
                .map(SysDept::getParentId)
                .collect(Collectors.toSet());

        Set<Long> deptIds = deptList.stream()
                .map(SysDept::getId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, deptIds);

        List<Option> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurDeptTreeOptions(rootId, deptList));
        }
        return list;
    }

    @Override
    public Long saveDept(DeptForm formData) {
        SysDept entity = deptConverter.form2Entity(formData);
        // Department path
        String treePath = generateDeptTreePath(formData.getParentId());
        entity.setTreePath(treePath);
        // Save department and return department ID
        this.save(entity);
        return entity.getId();
    }

    @Override
    public Long updateDept(Long deptId, DeptForm formData) {
        // form -> entity
        SysDept entity = deptConverter.form2Entity(formData);
        entity.setId(deptId);
        // Department path
        String treePath = generateDeptTreePath(formData.getParentId());
        entity.setTreePath(treePath);
        // Update department and return department ID
        this.updateById(entity);
        return entity.getId();
    }

    /**
     * Recursively generate department tree options
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<Option> recurDeptTreeOptions(long parentId, List<SysDept> deptList) {
        List<Option> list = CollectionUtil.emptyIfNull(deptList).stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .map(dept -> {
                    Option option = new Option(dept.getId(), dept.getName());
                    List<Option> children = recurDeptTreeOptions(dept.getId(), deptList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        option.setChildren(children);
                    }
                    return option;
                })
                .collect(Collectors.toList());
        return list;
    }

    /**
     * Delete departments
     *
     * @param ids Department IDs, multiple IDs separated by commas
     * @return
     */
    @Override
    public boolean deleteByIds(String ids) {
        // Delete departments and sub-departments
        if (StrUtil.isNotBlank(ids)) {
            String[] deptIds = ids.split(",");
            for (String deptId : deptIds) {
                this.remove(new LambdaQueryWrapper<SysDept>()
                        .eq(SysDept::getId, deptId)
                        .or()
                        .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", deptId));
            }
        }
        return true;
    }

    /**
     * Get department details
     *
     * @param deptId
     * @return
     */
    @Override
    public DeptForm getDeptForm(Long deptId) {
        SysDept entity = this.getOne(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getId, deptId)
                .select(
                        SysDept::getId,
                        SysDept::getName,
                        SysDept::getParentId,
                        SysDept::getStatus,
                        SysDept::getSort
                ));

        return deptConverter.entity2Form(entity);
    }

    /**
     * Generate department path
     *
     * @param parentId Parent ID
     * @return Parent node path separated by commas, e.g., 1,2,3
     */
    private String generateDeptTreePath(Long parentId) {
        String treePath = null;
        if (SystemConstants.ROOT_NODE_ID.equals(parentId)) {
            treePath = String.valueOf(parentId);
        } else {
            SysDept parent = this.getById(parentId);
            if (parent != null) {
                treePath = parent.getTreePath() + "," + parent.getId();
            }
        }
        return treePath;
    }
}
