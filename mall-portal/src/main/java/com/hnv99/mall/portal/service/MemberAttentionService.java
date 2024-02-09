package com.hnv99.mall.portal.service;

import com.hnv99.mall.portal.domain.MemberBrandAttention;
import org.springframework.data.domain.Page;

public interface MemberAttentionService {
    /**
     * Add attention to a brand
     */
    int add(MemberBrandAttention memberBrandAttention);

    /**
     * Remove attention from a brand
     */
    int delete(Long brandId);

    /**
     * Get the list of brands the user is paying attention to
     */
    Page<MemberBrandAttention> list(Integer pageNum, Integer pageSize);

    /**
     * Get details of the user's attention to a brand
     */
    MemberBrandAttention detail(Long brandId);

    /**
     * Clear the attention list
     */
    void clear();
}
