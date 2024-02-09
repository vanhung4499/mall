package com.hnv99.mall.portal.service;

import com.hnv99.mall.portal.domain.MemberProductCollection;
import org.springframework.data.domain.Page;

public interface MemberCollectionService {
    /**
     * Add to collection
     */
    int add(MemberProductCollection productCollection);

    /**
     * Delete from collection
     */
    int delete(Long productId);

    /**
     * Page query of collection
     */
    Page<MemberProductCollection> list(Integer pageNum, Integer pageSize);

    /**
     * View collection details
     */
    MemberProductCollection detail(Long productId);

    /**
     * Clear collection
     */
    void clear();
}
