package com.hnv99.mall.portal.service;

import com.hnv99.mall.portal.domain.MemberReadHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberReadHistoryService {
    /**
     * Create browsing history
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * Bulk delete browsing history
     */
    int delete(List<String> ids);

    /**
     * Get user browsing history with pagination
     */
    Page<MemberReadHistory> list(Integer pageNum, Integer pageSize);

    /**
     * Clear browsing history
     */
    void clear();
}
