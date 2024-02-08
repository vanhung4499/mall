package com.hnv99.mall.service;

import com.hnv99.mall.dto.SmsFlashPromotionProduct;
import com.hnv99.mall.model.SmsFlashPromotionProductRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsFlashPromotionProductRelationService {
    /**
     * Massively create relationships
     */
    @Transactional
    int create(List<SmsFlashPromotionProductRelation> relationList);

    /**
     * Modify the relationship information
     */
    int update(Long id, SmsFlashPromotionProductRelation relation);

    /**
     * Delete the relationship
     */
    int delete(Long id);

    /**
     * Get the relationship detail
     */
    SmsFlashPromotionProductRelation getItem(Long id);

    /**
     * Paging query related products and flash promotion information
     *
     * @param flashPromotionId        Flash promotion ID
     * @param flashPromotionSessionId Flash promotion session ID
     */
    List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);

    /**
     * Get the number of product relationships based on the promotion id and session id
     * @param flashPromotionId        Flash promotion ID
     * @param flashPromotionSessionId Flash promotion session ID
     */
    long getCount(Long flashPromotionId, Long flashPromotionSessionId);
}
