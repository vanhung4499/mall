package com.hnv99.mall.service;

import com.hnv99.mall.dto.SmsFlashPromotionSessionDetail;
import com.hnv99.mall.model.SmsFlashPromotionSession;

import java.util.List;

public interface SmsFlashPromotionSessionService {
    /**
     * Add session
     */
    int create(SmsFlashPromotionSession promotionSession);

    /**
     * Modify session
     */
    int update(Long id, SmsFlashPromotionSession promotionSession);

    /**
     * Modify session enable status
     */
    int updateStatus(Long id, Integer status);

    /**
     * Delete session
     */
    int delete(Long id);

    /**
     * Get details
     */
    SmsFlashPromotionSession getItem(Long id);

    /**
     * Get session list based on enable status
     */
    List<SmsFlashPromotionSession> list();

    /**
     * Get all selectable sessions and their quantity
     */
    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);
}
