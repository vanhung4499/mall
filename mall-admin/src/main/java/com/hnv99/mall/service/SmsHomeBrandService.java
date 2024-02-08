package com.hnv99.mall.service;

import com.hnv99.mall.model.SmsHomeBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeBrandService {
    /**
     * Add home brand recommendation
     */
    @Transactional
    int create(List<SmsHomeBrand> homeBrandList);

    /**
     * Modify brand recommendation sort order
     */
    int updateSort(Long id, Integer sort);

    /**
     * Batch delete brand recommendations
     */
    int delete(List<Long> ids);

    /**
     * Batch update recommendation status
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * Page query brand recommendations
     */
    List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
