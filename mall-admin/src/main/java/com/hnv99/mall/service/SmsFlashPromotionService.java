package com.hnv99.mall.service;

import com.hnv99.mall.model.SmsFlashPromotion;

import java.util.List;

public interface SmsFlashPromotionService {
    /**
     * Add promotion
     */
    int create(SmsFlashPromotion flashPromotion);

    /**
     * Modify specified promotion
     */
    int update(Long id, SmsFlashPromotion flashPromotion);

    /**
     * Delete single promotion
     */
    int delete(Long id);

    /**
     * Modify online/offline status
     */
    int updateStatus(Long id, Integer status);

    /**
     * Get promotion details
     */
    SmsFlashPromotion getItem(Long id);

    /**
     * Page query promotions
     */
    List<SmsFlashPromotion> list(String keyword, Integer pageSize, Integer pageNum);
}
