package com.hnv99.mall.service;

import com.hnv99.mall.dto.UmsMenuNode;
import com.hnv99.mall.model.UmsMenu;

import java.util.List;

public interface UmsMenuService {
    /**
     * Create a backend menu
     */
    int create(UmsMenu umsMenu);

    /**
     * Update a backend menu
     */
    int update(Long id, UmsMenu umsMenu);

    /**
     * Get menu details by ID
     */
    UmsMenu getItem(Long id);

    /**
     * Delete menu by ID
     */
    int delete(Long id);

    /**
     * Paginate query backend menu
     */
    List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * Return all menu lists in tree structure
     */
    List<UmsMenuNode> treeList();

    /**
     * Update menu display status
     */
    int updateHidden(Long id, Integer hidden);
}
