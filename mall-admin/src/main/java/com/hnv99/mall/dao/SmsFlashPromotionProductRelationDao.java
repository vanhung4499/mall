package com.hnv99.mall.dao;

import com.hnv99.mall.dto.SmsFlashPromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsFlashPromotionProductRelationDao {
    /**
     * Get flash sale and related product information
     */
    List<SmsFlashPromotionProduct> getList(@Param("flashPromotionId") Long flashPromotionId, @Param("flashPromotionSessionId") Long flashPromotionSessionId);
}
